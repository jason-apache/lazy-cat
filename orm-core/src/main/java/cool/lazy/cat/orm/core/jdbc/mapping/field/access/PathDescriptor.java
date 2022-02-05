package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

/**
 * @author: mahao
 * @date: 2021/8/23 09:41
 * 路径描述
 */
public interface PathDescriptor {

    /**
     * @return 完整路径
     */
    String getFullPath();

    /**
     * @return 是否是嵌套的字段
     */
    boolean nested();

    /**
     * 给定一个路径描述 判断当前路径是否处于改路径之上
     * @param pathDescriptor 路径描述
     * @return 是否处于链路
     */
    default boolean belong(PathDescriptor pathDescriptor) {
        return this.belong(pathDescriptor.getFullPath());
    }

    /**
     * 给定一个路径 判断当前路径是否处于改路径之上
     * @param fullPath 路径
     * @return 是否处于链路
     */
    boolean belong(String fullPath);
}
