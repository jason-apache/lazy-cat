package cool.lazy.cat.orm.core.jdbc.sql.string.condition;

/**
 * @author: mahao
 * @date: 2021/10/11 14:48
 * 条件连接关系
 */
public enum CombinationType {

    /**
     * 无
     */
    NONE,
    /**
     * '并且'关系
     */
    AND,
    /**
     * '或'关系
     */
    OR
}
