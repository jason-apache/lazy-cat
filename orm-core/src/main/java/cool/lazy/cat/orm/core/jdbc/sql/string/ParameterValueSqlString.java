package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.base.constant.Case;

/**
 * @author: mahao
 * @date: 2022-01-25 11:16
 * 表示赋值的sqlString实例
 */
public interface ParameterValueSqlString extends NormalSqlString, SqlString {

    /**
     * @return 参数名实例
     */
    ParameterNameSqlString getParameterName();

    /**
     * eg: set name = :name
     * @return 赋值符号
     */
    default String getSymbol() {
        return " = ";
    }

    /**
     * 无需关心大小写
     * @param charCase 字符大小写
     */
    @Override
    default void changeCase(Case charCase) {}
}
