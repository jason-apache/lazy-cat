package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.OneToManyMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2022-02-01 16:21
 */
public class FakeOneToManyPojoMappingImpl implements FakePojoMapping, OneToManyMapping, PojoMapping {

    protected final OneToManyMapping realPojoMapping;
    private boolean insertable;
    private boolean updatable;
    private boolean deletable;

    protected FakeOneToManyPojoMappingImpl(OneToManyMapping realPojoMapping) {
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

    @Override
    public Class<? extends Collection<?>> getContainerType() {
        return realPojoMapping.getContainerType();
    }
}
