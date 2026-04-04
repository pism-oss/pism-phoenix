package cn.com.pism.phoenix.core.config.mybatis.plugin;

import cn.com.pism.phoenix.core.common.ComEntity;
import cn.com.pism.phoenix.core.util.AuthUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static cn.com.pism.phoenix.core.common.ComEntity.*;
import static com.baomidou.mybatisplus.core.toolkit.Constants.MP_FILL_ET;
import static com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.*;

/**
 * 逻辑删除拦截器
 *
 * @author perccyking
 * @since 26-03-24 23:44
 */
@Component
@Slf4j
public class LogicDeletedInnerInterceptor implements InnerInterceptor {

    private static final String UPDATE = "update";

    private static final String PARAMETER_MAPPINGS = "parameterMappings";

    private static final String CONFIGURATION = "configuration";

    @Override
    @SneakyThrows
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        BoundSql boundSql = sh.getBoundSql();
        String originalSql = boundSql.getSql();

        // 解析并对 sql 进行验证
        Update update = parseAndValidateSql(originalSql);

        if (update == null) {
            return;
        }

        // 判断是否为逻辑删除语句，并标记为了已删除
        if (isLogicDelete(update, boundSql)) {

            // 增强逻辑删除sql
            enhanceLogicDeletedSql(sh, boundSql, update);
        }
    }

    /**
     * <p>
     * 解析并对 sql 进行解析
     * </p>
     * by perccyking
     *
     * @param sql : sql
     * @return {@link Update} 解析后的 update 对象，如果解析失败返回 null
     * @since 26-04-04 21:07
     */
    private Update parseAndValidateSql(String sql) {

        // 空sql 不处理
        if (StringUtils.isBlank(sql)) {
            return null;
        }

        String trimSql = sql.trim().toLowerCase();
        // 非 update 不处理
        if (!trimSql.startsWith(UPDATE)) {
            return null;
        }

        // 解析为 update 对象
        try {
            Update update = (Update) CCJSqlParserUtil.parse(sql);
            List<UpdateSet> updateSets = update.getUpdateSets();
            if (updateSets == null || updateSets.isEmpty()) {
                return null;
            }
            return update;
        } catch (Exception e) {
            log.warn("failed to parse sql: {}", sql, e);
            return null;
        }
    }

    /**
     * <p>
     * 增强逻辑删除sql
     * </p>
     * by perccyking
     *
     * @param sh       : sh
     * @param boundSql : boundSql
     * @param update   : Update
     * @since 26-04-04 22:51
     */
    private void enhanceLogicDeletedSql(StatementHandler sh, BoundSql boundSql, Update update) {
        // 添加逻辑删除参数
        addLogicDeletedParamsToBoundSql(boundSql);

        // 修改参数映射
        List<ParameterMapping> modifiedMappings = modifyParameterMappings(sh, boundSql, update);

        // 将修改后的sql和修改后的参数映射，更新到 boundSql 中
        updateBoundSql(boundSql, update, modifiedMappings);
    }

    private void updateBoundSql(BoundSql boundSql, Update update, List<ParameterMapping> modifiedMappings) {
        String modifiedSql = update.toString();

        MetaObject metaBoundSql = SystemMetaObject.forObject(boundSql);
        metaBoundSql.setValue(SQL, modifiedSql);
        metaBoundSql.setValue(PARAMETER_MAPPINGS, modifiedMappings);
    }

    private List<ParameterMapping> modifyParameterMappings(StatementHandler sh, BoundSql boundSql, Update update) {
        // sql 中是否有 delete_by
        AtomicBoolean hasDeleteBy = new AtomicBoolean(false);

        // sql 中是否有 delete_time
        AtomicBoolean hasDeleteTime = new AtomicBoolean(false);

        // 检查 updateSet中的列，是否包含删除人和删除时间
        checkUpdateSetsColumns(update, hasDeleteBy, hasDeleteTime);

        // 过滤出不包含 update_by 和 update_time 的参数映射
        List<ParameterMapping> modifiedMappings = filterParameterMappings(boundSql);

        // 添加必要的参数
        addRequiredParameterMappings(sh, update, hasDeleteBy, hasDeleteTime, modifiedMappings);

        return modifiedMappings;
    }

    private void addRequiredParameterMappings(StatementHandler sh, Update update,
                                              AtomicBoolean hasDeleteBy, AtomicBoolean hasDeleteTime,
                                              List<ParameterMapping> modifiedMappings) {
        // jdbcParam 数量
        int setParamCount = countJdbcParametersInUpdateSets(update.getUpdateSets());

        MetaObject metaParameterHandler = SystemMetaObject.forObject(sh.getParameterHandler());
        Configuration configuration = (Configuration) metaParameterHandler.getValue(CONFIGURATION);

        if (!hasDeleteBy.get()) {
            addUpdateSetToSql(update, COL_DELETE_BY);
            modifiedMappings.add(setParamCount,
                    new ParameterMapping.Builder(configuration, FIELD_DELETE_BY, Object.class).build());
            setParamCount++;
        }

        if (!hasDeleteTime.get()) {
            addUpdateSetToSql(update, COL_DELETE_TIME);
            modifiedMappings.add(setParamCount,
                    new ParameterMapping.Builder(configuration, FIELD_DELETE_TIME, Long.class).build());
        }
    }

    /**
     * <p>
     * 检查 UpdateSets 中是否包含 删除人和删除时间 列
     * </p>
     * by perccyking
     *
     * @param update        : Update
     * @param hasDeleteBy   : 是否有删除人标记
     * @param hasDeleteTime : 是否有删除时间标记
     * @since 26-04-04 23:06
     */
    private void checkUpdateSetsColumns(Update update, AtomicBoolean hasDeleteBy, AtomicBoolean hasDeleteTime) {
        Iterator<UpdateSet> iterator = update.getUpdateSets().iterator();
        while (iterator.hasNext()) {
            UpdateSet set = iterator.next();
            List<Column> columns = set.getColumns();
            for (Column column : columns) {
                String columnName = column.getColumnName().replace(BACKTICK, EMPTY).toLowerCase();

                // 移除update_time和update_by的set
                if (COL_UPDATE_TIME.equals(columnName) || COL_UPDATE_BY.equals(columnName)) {
                    iterator.remove();
                    break;
                }

                // 检查是否已存在delete_by和delete_time
                if (COL_DELETE_BY.equals(columnName)) {
                    hasDeleteBy.set(true);
                }
                if (COL_DELETE_TIME.equals(columnName)) {
                    hasDeleteTime.set(true);
                }
            }
        }
    }

    /**
     * <p>
     * 过滤出不包含 更新人和更新时间的 参数映射
     * </p>
     * by perccyking
     *
     * @param boundSql : boundSql
     * @return {@link ParameterMapping} 参数映射
     * @since 26-04-04 23:03
     */
    private List<ParameterMapping> filterParameterMappings(BoundSql boundSql) {
        List<ParameterMapping> originalMappings = boundSql.getParameterMappings();
        return new ArrayList<>(originalMappings.stream()
                .filter(mapping -> !mapping.getProperty().endsWith(FIELD_UPDATE_BY) &&
                        !mapping.getProperty().endsWith(FIELD_UPDATE_TIME))
                .toList());
    }


    /**
     * <p>
     * 添加逻辑删除参数到BoundSql
     * </p>
     * by perccyking
     *
     * @param boundSql : boundSql
     * @since 26-04-04 22:43
     */
    private void addLogicDeletedParamsToBoundSql(BoundSql boundSql) {

        // boundSql 中的参数
        Object parameterObject = boundSql.getParameterObject();

        // 操作人id
        Long operatorId = AuthUtil.getLoginId();

        // 当前时间戳
        long currentTime = System.currentTimeMillis();

        if (parameterObject instanceof Map<?, ?> paramObjectMap) {
            // 添加逻辑删除参数
            doAddLogicDeletedParamsToBoundSql(paramObjectMap, operatorId, currentTime);
        }

    }

    /**
     * <p>
     * 添加逻辑删除到BoundSql中
     * </p>
     * by perccyking
     *
     * @param paramObjectMap : 参数map
     * @param operatorId     : 操作人id
     * @param currentTime    : 当前时间戳
     * @since 26-04-04 22:45
     */
    private void doAddLogicDeletedParamsToBoundSql(Map<?, ?> paramObjectMap, Long operatorId, long currentTime) {
        // 填充实体，更新 ComEntity
        if (paramObjectMap.containsKey(MP_FILL_ET)) {
            Object fillEt = paramObjectMap.get(MP_FILL_ET);
            if (fillEt instanceof ComEntity fillComEntity) {
                fillComEntity.setDeleteBy(operatorId);
                fillComEntity.setDeleteTime(currentTime);
            }
        }

        // 如果是 LambdaUpdateWrapper 更新 parameterObject
        if (paramObjectMap.containsKey(WRAPPER)) {
            Object wrapper = paramObjectMap.get(WRAPPER);
            if (wrapper instanceof LambdaUpdateWrapper<?>) {
                MetaObject paramObjectMapMetaObject = SystemMetaObject.forObject(paramObjectMap);
                paramObjectMapMetaObject.setValue(FIELD_DELETE_BY, operatorId);
                paramObjectMapMetaObject.setValue(FIELD_DELETE_TIME, currentTime);
            }
        }
    }

    /**
     * <p>
     * 统计 JdbcParameter 的数量
     * </p>
     * by perccyking
     *
     * @param updateSets : set 子句列表
     * @return {@link int} JdbcParameter 参数
     * @since 26-04-02 23:58
     */
    private int countJdbcParametersInUpdateSets(List<UpdateSet> updateSets) {
        int count = 0;
        for (UpdateSet set : updateSets) {
            for (Expression value : set.getValues()) {
                if (value instanceof JdbcParameter) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * <p>
     * 在 sql 的 set 子句中添加列
     * </p>
     * by perccyking
     *
     * @param update : update语句
     * @param column : 列名
     * @since 26-04-02 23:45
     */
    private void addUpdateSetToSql(Update update, String column) {
        UpdateSet updateSet = new UpdateSet();
        updateSet.add(new Column(column), new JdbcParameter());
        update.getUpdateSets().add(updateSet);
    }


    /**
     * <p>
     * 是否为逻辑删除
     * </p>
     * by perccyking
     *
     * @param update   : update语句
     * @param boundSql : sql
     * @return {@link boolean} true 是逻辑删除语句
     * @since 26-04-02 23:43
     */
    private boolean isLogicDelete(Update update, BoundSql boundSql) {
        String tableName = update.getTable().getName();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);

        if (tableInfo == null) {
            return false;
        }

        TableFieldInfo logicDeleteFieldInfo = tableInfo.getLogicDeleteFieldInfo();
        if (logicDeleteFieldInfo == null) {
            return false;
        }

        return checkUpdateSetsForLogicDelete(update, boundSql, logicDeleteFieldInfo);
    }

    /**
     * <p>
     * 检查UpdateSets中是否包含逻辑删除操作
     * </p>
     * by perccyking
     *
     * @param update               : Update
     * @param boundSql             : boundSql
     * @param logicDeleteFieldInfo : 逻辑删除字段信息
     * @return {@link boolean} true表示包含逻辑删除操作
     * @since 26-04-04 22:41
     */
    private boolean checkUpdateSetsForLogicDelete(Update update, BoundSql boundSql, TableFieldInfo logicDeleteFieldInfo) {
        for (UpdateSet set : update.getUpdateSets()) {
            ExpressionList<Column> columns = set.getColumns();
            ExpressionList<?> values = set.getValues();

            for (int i = 0; i < columns.size(); i++) {
                // 去除可能存在的反引号
                String col = columns.get(i).getColumnName().replace(BACKTICK, EMPTY);
                // 列对应的值(表达式)
                Expression expression = values.get(i);

                // 列名和逻辑删除的列名一致
                if (logicDeleteFieldInfo.getColumn().equalsIgnoreCase(col)) {
                    return checkLogicDeleteValue(boundSql, expression, logicDeleteFieldInfo);
                }
            }
        }
        return false;
    }


    /**
     * <p>
     * 检查逻辑删除值是否匹配
     * </p>
     * by perccyking
     *
     * @param boundSql             : boundSql
     * @param expression           : 列表达式
     * @param logicDeleteFieldInfo : 逻辑删除列配置信息
     * @return {@link boolean} true: 以标记逻辑删除
     * @since 26-04-04 22:01
     */
    private boolean checkLogicDeleteValue(BoundSql boundSql, Expression expression, TableFieldInfo logicDeleteFieldInfo) {
        // 逻辑删除值
        String logicDeleteValue = logicDeleteFieldInfo.getLogicDeleteValue();

        if (expression instanceof LongValue longValue) {
            return logicDeleteValue.equalsIgnoreCase(longValue.getStringValue());
        }
        if (expression instanceof StringValue stringValue) {
            return logicDeleteValue.equalsIgnoreCase(stringValue.getValue());
        }

        // 表达式为 jdbcParameter
        if (expression instanceof JdbcParameter jdbcParameter) {
            return checkJdbcParameterLogicDelete(boundSql, jdbcParameter, logicDeleteFieldInfo);
        }
        log.warn("expression not supported {}", expression.getClass());

        return false;
    }

    /**
     * <p>
     * 检查 jdbc 参数中 是否有逻辑删除值，并且是已删除
     * </p>
     * by perccyking
     *
     * @param boundSql             : boundSql
     * @param jdbcParameter        : jdbc 参数
     * @param logicDeleteFieldInfo : 逻辑删除字段配置信息
     * @return {@link boolean} true 是逻辑删除，并且已经被标记为删除
     * @since 26-04-04 21:55
     */
    private boolean checkJdbcParameterLogicDelete(BoundSql boundSql, JdbcParameter jdbcParameter, TableFieldInfo logicDeleteFieldInfo) {
        Integer index = jdbcParameter.getIndex();
        Object value = SystemMetaObject.forObject(boundSql.getParameterObject())
                .getValue(boundSql.getParameterMappings().get(index - 1).getProperty());

        if (value instanceof Boolean deleted) {
            return deleted.equals(BooleanUtils.toBoolean(logicDeleteFieldInfo.getLogicDeleteValue()));
        }
        if (value instanceof String deleted) {
            return logicDeleteFieldInfo.getLogicDeleteValue().equalsIgnoreCase(deleted);
        }
        return false;
    }
}