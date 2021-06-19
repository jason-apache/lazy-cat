package cool.lazy.cat.orm.core.jdbc.dto;


import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

/**
 * @author: mahao
 * @date: 2021/3/16 14:17
 * 嵌套属性包装类
 * 嵌套的属性可能是一个对象中的值，也可能是一个完整的对象
 *      eg：
 *          parent.name 代表类中 parent对象中的name属性
 *          parent.parent 代表类中 parent对象中的parent映射对象
 */
public class TableFieldInfoWrapper {

    /**
     * 标注该嵌套属性所属的链表结构
     */
    private TableChain tableChain;
    /**
     * 属性详细信息
     */
    private TableFieldInfo fieldInfo;

    public TableFieldInfoWrapper(TableChain tableChain, TableFieldInfo fieldInfo) {
        this.tableChain = tableChain;
        this.fieldInfo = fieldInfo;
    }

    public TableChain getTableChain() {
        return tableChain;
    }

    public TableFieldInfoWrapper setTableChain(TableChain tableChain) {
        this.tableChain = tableChain;
        return this;
    }

    public TableFieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public TableFieldInfoWrapper setFieldInfo(TableFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        return this;
    }
}
