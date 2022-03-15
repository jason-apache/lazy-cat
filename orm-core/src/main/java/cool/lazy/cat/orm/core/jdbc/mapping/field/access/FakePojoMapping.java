package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.base.constant.JoinMode;
import cool.lazy.cat.orm.core.jdbc.mapping.On;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.List;

/**
 * @author: mahao
 * @date: 2022-02-01 16:41
 */
public interface FakePojoMapping extends PojoMapping {

    PojoMapping getRealPojoMapping();

    void setInsertable(boolean insertable);

    void setUpdatable(boolean updatable);

    void setDeletable(boolean deletable);

    @Override
    default Class<?> getJavaType() {
        return getRealPojoMapping().getJavaType();
    }

    @Override
    default List<On> getJoinCondition() {
        return getRealPojoMapping().getJoinCondition();
    }

    @Override
    default PojoField getPojoField() {
        return getRealPojoMapping().getPojoField();
    }

    @Override
    default int getCascadeLevel() {
        return getRealPojoMapping().getCascadeLevel();
    }

    @Override
    default String[] getCascadeScope() {
        return getRealPojoMapping().getCascadeScope();
    }

    @Override
    default String[] getIgnoreFields() {
        return getRealPojoMapping().getIgnoreFields();
    }

    @Override
    default int sort() {
        return getRealPojoMapping().sort();
    }

    @Override
    default boolean havingMappedToSource() {
        return getRealPojoMapping().havingMappedToSource();
    }

    @Override
    default void setHavingMappedToSource(boolean havingMappedToSource) {
        getRealPojoMapping().setHavingMappedToSource(havingMappedToSource);
    }

    @Override
    default JoinMode getJoinMode() {
        return getRealPojoMapping().getJoinMode();
    }
}
