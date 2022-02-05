package cool.lazy.cat.orm.core.jdbc.sql.string;

/**
 * @author: mahao
 * @date: 2021/10/13 14:15
 * 表示具备名称/别名的sqlString
 */
public interface NameSqlString extends SqlString {

    /**
     * @return 名称
     */
    String getName();

    /**
     * @return 别名
     */
    String getAliasName();
}
