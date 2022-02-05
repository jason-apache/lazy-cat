package cool.lazy.cat.orm.core.jdbc.sql.string;

/**
 * @author: mahao
 * @date: 2021/10/13 14:13
 * 表示操作数据库对象的sqlString实例
 */
public interface OperationObjectSqlString {

    /**
     * @return 名称描述实例
     */
    NameSqlString getObjectNameSql();
}
