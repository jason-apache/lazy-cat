package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.constant.Case;

/**
 * @author: mahao
 * @date: 2022-01-25 10:44
 * 表示参数名的sqlString实例
 */
public interface ParameterNameSqlString extends FieldSqlString, SqlString {

    /**
     * @return 参数名称
     */
    String getName();

    /**
     * 使用NamedParameterJdbcTemplate传参的必要参数
     * @return 取值符号
     */
    default String getSymbol() {
        return ":";
    }

    /**
     * 参数名实例不能改变大小写 否则将与sqlSource中的属性名无法对应
     * @param charCase 字符大小写
     */
    @Override
    default void changeCase(Case charCase) {}
}
