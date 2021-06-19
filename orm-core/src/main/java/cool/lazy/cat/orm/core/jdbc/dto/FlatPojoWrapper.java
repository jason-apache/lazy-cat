package cool.lazy.cat.orm.core.jdbc.dto;

/**
 * @author: mahao
 * @date: 2021/3/13 10:40
 * 平铺的实例包装类
 */
public class FlatPojoWrapper {

    private int index;
    private Object pojoInstance;

    public FlatPojoWrapper(int index, Object pojoInstance) {
        this.index = index;
        this.pojoInstance = pojoInstance;
    }

    public int getIndex() {
        return index;
    }

    public FlatPojoWrapper setIndex(int index) {
        this.index = index;
        return this;
    }

    public Object getPojoInstance() {
        return pojoInstance;
    }

    public FlatPojoWrapper setPojoInstance(Object pojoInstance) {
        this.pojoInstance = pojoInstance;
        return this;
    }
}
