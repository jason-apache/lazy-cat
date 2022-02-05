package cool.lazy.cat.orm.core.jdbc.param.operation;

import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/11/2 13:04
 */
public class DataOperationItemImpl implements DataOperationItem {

    private final Class<?> pojoType;
    private SqlCondition condition;
    private List<Object> items = Collections.emptyList();

    public DataOperationItemImpl(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    @Override
    public SqlCondition getCondition() {
        return condition;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public void setCondition(SqlCondition condition) {
        this.condition = condition;
    }

    @Override
    public void add(Object pojo) {
        if (items == Collections.EMPTY_LIST) {
            items = new ArrayList<>();
        }
        items.add(pojo);
    }

    @Override
    public Iterator<Object> iterator() {
        return items.iterator();
    }

    @Override
    public Collection<Object> values() {
        return items;
    }
}
