package cn.com.pism.phoenix.core.config.mybatis.plugin;

import cn.com.pism.exception.PismException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * 游标分页插件
 *
 * @author perccyking
 * @since 26-03-29 12:24
 */
@Component
public class CursorPaginationInterceptor implements InnerInterceptor {

    private static final String CURSOR_COLUM = "id";

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // 获取查询参数
        Optional<IPage> pageOptional = ParameterUtils.findPage(parameter);
        if (pageOptional.isEmpty()) {
            return;
        }

        IPage<?> page = pageOptional.get();

        // 是否包含游标翻页参数
        if (page instanceof CursorPage<?> cursorPage) {

            // 关闭总数统计
            cursorPage.setSearchCount(false);
            cursorPage.setTotal(0L);

            // 游标值为空代表查询第一页，无需追加 WHERE 条件
            if (cursorPage.getCursorValue() == null) {
                cursorPage.setCurrent(1);
                return;
            }

            // 解析查询sql
            try {
                Statement statement = CCJSqlParserUtil.parse(boundSql.getSql());
                PlainSelect plainSelect = null;

                if (statement instanceof PlainSelect plainSelect1) {
                    plainSelect = plainSelect1;
                } else if (statement instanceof Select select) {
                    plainSelect = select.getPlainSelect();
                }

                if (plainSelect != null) {
                    // 1. 【智能化】从原 SQL 中提取游标列和排序方向
                    String actualColumn = CURSOR_COLUM;
                    boolean isAsc = false; // 默认降序
                    List<OrderByElement> orderByElements = plainSelect.getOrderByElements();

                    if (orderByElements != null && !orderByElements.isEmpty()) {
                        OrderByElement firstOrder = orderByElements.getFirst();
                        // 从 XML 的 ORDER BY 提取出真实的列名 (如 create_time) 和 升降序
                        actualColumn = firstOrder.getExpression().toString();
                        isAsc = firstOrder.isAsc();
                    }

                    // 2. 追加 WHERE 游标条件 (> 或 <)
                    Expression cursorCondition = buildCursorCondition(actualColumn, cursorPage.getCursorValue(), isAsc, cursorPage.isForward());
                    if (plainSelect.getWhere() == null) {
                        plainSelect.setWhere(cursorCondition);
                    } else {
                        plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), cursorCondition));
                    }

                    // 3. 【智能化】如果是上一页，自动反转 SQL 的 ORDER BY 方向
                    if (!cursorPage.isForward() && orderByElements != null) {
                        for (OrderByElement element : orderByElements) {
                            element.setAsc(!element.isAsc());
                            element.setAscDescPresent(true);
                        }
                    }

                    PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
                    mpBoundSql.sql(statement.toString());
                }
            } catch (Exception e) {
                throw new PismException("解析游标分页 SQL 异常", e);
            }

            cursorPage.setCurrent(1);
        }
    }

    /**
     * <p>
     * 构建条件表达式
     * </p>
     * by perccyking
     *
     * @param columnStr   :
     * @param cursorValue :
     * @param isAsc       :
     * @param forward     :
     * @return {@link Expression} 表达式
     * @since 26-04-06 23:53
     */
    private Expression buildCursorCondition(String columnStr, Object cursorValue, boolean isAsc, boolean forward) {
        Column column = new Column(columnStr);
        Expression value;

        // 判断游标值类型
        if (cursorValue instanceof Number) {
            value = new LongValue(cursorValue.toString());
        } else {
            // 时间类型或字符串类型，必须转成单引号包裹的 StringValue
            value = new StringValue(cursorValue.toString());
        }

        // 核心真值表：根据提取到的 ASC/DESC 和 翻页方向 决定用 > 还是 <
        if (isAsc == forward) {
            GreaterThan greaterThan = new GreaterThan();
            greaterThan.setLeftExpression(column);
            greaterThan.setRightExpression(value);
            return greaterThan;
        } else {
            MinorThan minorThan = new MinorThan();
            minorThan.setLeftExpression(column);
            minorThan.setRightExpression(value);
            return minorThan;
        }
    }

}
