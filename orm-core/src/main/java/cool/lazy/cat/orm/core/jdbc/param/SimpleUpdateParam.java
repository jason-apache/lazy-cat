package cool.lazy.cat.orm.core.jdbc.param;


import cool.lazy.cat.orm.core.jdbc.condition.Condition;

/**
 * @author: mahao
 * @date: 2021/4/14 10:53
 */
public class SimpleUpdateParam implements UpdateParam {

    private Class<?> pojoType;
    private Condition condition;
    private boolean ignoreNull;
    private String[] ignoreFields;
    private Object data;

    public SimpleUpdateParam() {
    }

    public SimpleUpdateParam(UpdateParam updateParam) {
        this.pojoType = updateParam.getPojoType();
        this.condition = updateParam.getCondition();
        this.ignoreNull = updateParam.getIgnoreNull();
        this.ignoreFields = updateParam.getIgnoreFields();
        this.data = updateParam.getData();
    }

    public SimpleUpdateParam(Class<?> pojoType, Condition condition, boolean ignoreNull, String[] ignoreFields, Object data) {
        this.pojoType = pojoType;
        this.condition = condition;
        this.ignoreNull = ignoreNull;
        this.ignoreFields = ignoreFields;
        this.data = data;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    public SimpleUpdateParam setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    public SimpleUpdateParam setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public boolean getIgnoreNull() {
        return ignoreNull;
    }

    public SimpleUpdateParam setIgnoreNull(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
        return this;
    }

    @Override
    public String[] getIgnoreFields() {
        return ignoreFields;
    }

    public SimpleUpdateParam setIgnoreFields(String[] ignoreFields) {
        this.ignoreFields = ignoreFields;
        return this;
    }

    @Override
    public Object getData() {
        return data;
    }

    public SimpleUpdateParam setData(Object data) {
        this.data = data;
        return this;
    }
}
