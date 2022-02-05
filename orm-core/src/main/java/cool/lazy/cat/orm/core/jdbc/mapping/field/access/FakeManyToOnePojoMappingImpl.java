package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.ManyToOneMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;

/**
 * @author: mahao
 * @date: 2022-02-01 16:21
 */
public class FakeManyToOnePojoMappingImpl implements FakePojoMapping, ManyToOneMapping, PojoMapping {

    protected final ManyToOneMapping realPojoMapping;
    private boolean insertable;
    private boolean updatable;
    private boolean deletable;

    public FakeManyToOnePojoMappingImpl(ManyToOneMapping realPojoMapping) {
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
