package cool.lazy.cat.orm.core.manager.subject;


import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;

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

    public void setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }
}
