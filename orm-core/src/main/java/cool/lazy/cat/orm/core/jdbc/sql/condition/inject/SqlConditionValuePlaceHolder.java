package cool.lazy.cat.orm.core.jdbc.sql.condition.inject;

/**
 * @author: mahao
 * @date: 2022-01-18 17:18
 * sql条件值选取占位符 等价于 where id = #{id}
 */
@FunctionalInterface
public interface SqlConditionValuePlaceHolder {

    /**
     * @return 字段名称
     */
    String getFieldName();
}
