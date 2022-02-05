package cool.lazy.cat.orm.core.jdbc.sql;

/**
 * @author: mahao
 * @date: 2021/10/13 15:25
 * 数据库对象名称
 */
public interface ObjectName {

    /**
     * @return 库
     */
    String getSchema();

    /**
     * @return 对象名称
     */
    String getName();
}
