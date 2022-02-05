package cool.lazy.cat.orm.core.jdbc.param;

/**
 * @author: mahao
 * @date: 2021/7/29 13:34
 */
public interface DataHolderParam extends Param {

    /**
     * 持有的源数据，可以是一个对象，也可以是一个对象集合
     * @return 数据
     */
    Object getData();

    /**
     * @return 是否同步更新映射对象
     */
    boolean cascade();
}
