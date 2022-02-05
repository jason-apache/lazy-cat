package cool.lazy.cat.orm.core.jdbc.sql.source;

/**
 * @author: mahao
 * @date: 2021/9/20 21:33
 * 使用pojo对象作为sql参数载体
 */
public interface PojoSqlSource extends SqlSource {

    /**
     * @return pojo对象引用
     */
    Object getPojo();
}
