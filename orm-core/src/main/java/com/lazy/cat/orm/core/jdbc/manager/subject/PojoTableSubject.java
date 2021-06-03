package com.lazy.cat.orm.core.jdbc.manager.subject;


import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import com.lazy.cat.orm.core.manager.subject.Subject;

/**
 * @author: mahao
 * @date: 2021/3/28 15:50
 */
public class PojoTableSubject implements Subject {

    protected Class<?> pojoType;
    protected TableInfo tableInfo;

    public Class<?> getPojoType() {
        return pojoType;
    }

    public PojoTableSubject setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public PojoTableSubject setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        return this;
    }
}
