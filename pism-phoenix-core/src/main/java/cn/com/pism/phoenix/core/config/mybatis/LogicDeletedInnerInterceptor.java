package cn.com.pism.phoenix.core.config.mybatis;

import cn.com.pism.phoenix.core.common.ComEntity;
import cn.com.pism.phoenix.core.util.AuthUtil;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static cn.com.pism.phoenix.core.common.ComEntity.*;
import static com.baomidou.mybatisplus.core.toolkit.Constants.MP_FILL_ET;
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

    @Override
    @SneakyThrows
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        BoundSql boundSql = sh.getBoundSql();
        String originalSql = boundSql.getSql();
        if (StringUtils.isBlank(originalSql)) {
            return;
        }
        String trimSql = originalSql.trim().toLowerCase();

        // 只处理update
        if (!trimSql.startsWith(UPDATE)) {
            return;
        }

        Update update = (Update) CCJSqlParserUtil.parse(originalSql);
        List<UpdateSet> updateSets = update.getUpdateSets();
        if (updateSets == null || updateSets.isEmpty()) {
            return;
        }

        // 判断是否为逻辑删除
        boolean isDelete = isLogicDelete(update);

        if (isDelete) {

            // 增强逻辑删除sql
            enhanceLogicDeletedSql(boundSql, update);
        }
    }

    private void enhanceLogicDeletedSql(BoundSql boundSql, Update update) {
        // 填充删除人和删除时间
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject instanceof Map<?, ?> paramObjectMap) {
            Object fillEt = paramObjectMap.get(MP_FILL_ET);
            if (fillEt instanceof ComEntity fillComEntity) {
                fillComEntity.setDeleteBy(AuthUtil.getLoginId());
                fillComEntity.setDeleteTime(System.currentTimeMillis());
            }
        }


        Iterator<UpdateSet> iterator = update.getUpdateSets().iterator();

        while (iterator.hasNext()) {
            UpdateSet set = iterator.next();
            set.getColumns().forEach(column -> {
                String columnName = column.getColumnName().replace(BACKTICK, EMPTY).toLowerCase();
                // 剔除更新时间和更新人
                if (COL_UPDATE_TIME.equals(columnName) || COL_UPDATE_BY.equals(columnName)) {
                    iterator.remove();
                }
            });

        }

        // 修改后的sql
        String modifiedSql = update.toString();

        List<ParameterMapping> originalMappings = boundSql.getParameterMappings();

        List<ParameterMapping> modifiedMappings = originalMappings.stream().filter(mapping -> {
            String property = mapping.getProperty();
            // 剔除参数中的 updateBy 和 updateTime
            return !property.endsWith(FIELD_UPDATE_BY) && !property.endsWith(FIELD_UPDATE_TIME);
        }).toList();

        MetaObject metaBoundSql = SystemMetaObject.forObject(boundSql);
        metaBoundSql.setValue(SQL, modifiedSql);
        metaBoundSql.setValue(PARAMETER_MAPPINGS, modifiedMappings);
    }

    private boolean isLogicDelete(Update update) {
        String tableName = update.getTable().getName();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
        // 检查sql中是否有逻辑删除字段
        TableFieldInfo logicDeleteFieldInfo = tableInfo.getLogicDeleteFieldInfo();
        for (UpdateSet set : update.getUpdateSets()) {
            ExpressionList<Column> columns = set.getColumns();
            ExpressionList<?> values = set.getValues();

            for (int i = 0; i < columns.size(); i++) {
                // 去除可能存在的反引号
                String col = columns.get(i).getColumnName().replace(BACKTICK, EMPTY);
                // 列对应的值
                Expression val = values.get(i);

                // 列名和逻辑删除的列名一致
                if (logicDeleteFieldInfo.getColumn().equalsIgnoreCase(col)) {
                    if (val instanceof LongValue longValue) {
                        return logicDeleteFieldInfo.getLogicDeleteValue().equalsIgnoreCase(longValue.getStringValue());
                    }
                    log.warn("expression not supported {}", val.getClass());

                }
            }
        }
        return false;
    }
}