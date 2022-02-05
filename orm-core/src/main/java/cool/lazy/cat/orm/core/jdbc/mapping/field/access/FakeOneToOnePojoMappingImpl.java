package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.OneToOneMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;

/**
 * @author: mahao
 * @date: 2022-02-01 16:21
 */
public class FakeOneToOnePojoMappingImpl implements FakePojoMapping, OneToOneMapping, PojoMapping {

    protected final OneToOneMapping realPojoMapping;
    private boolean insertable;
    private boolean updatable;
    private boolean deletable;

    public FakeOneToOnePojoMappingImpl(OneToOneMapping realPojoMapping) {
        this.realPojoMapping = realPojoMapping;
    }

    @Override
    public PojoMapping getRealPojoMapping() {
        return realPojoMapping;
    }

    @Override
    public boolean isInsertable() {
        return this.insertable;
    }

    @Override
    public boolean isUpdatable() {
        return this.updatable;
    }

    @Override
    public boolean isDeletable() {
        return this.deletable;
    }

    @Override
    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    @Override
    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    @Override
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
