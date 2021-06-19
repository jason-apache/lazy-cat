package cool.lazy.cat.orm.core.jdbc.holder;

import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/4/13 08:58
 * tableChain包装类
 */
public class TableChainHolder {

    private TableInfo tableInfo;
    private List<TableChain> nestedChain;
    private List<TableChain> flatChain;

    public TableChainHolder(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        this.nestedChain = tableInfo.getNestedChain();
        this.flatChain = tableInfo.getFlatChain();
    }

    public List<TableChain> getNestedChain() {
        return nestedChain;
    }

    public TableChainHolder setNestedChain(List<TableChain> nestedChain) {
        this.nestedChain = nestedChain;
        return this;
    }

    public List<TableChain> getFlatChain() {
        return flatChain;
    }

    public TableChainHolder setFlatChain(List<TableChain> flatChain) {
        this.flatChain = flatChain;
        return this;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public TableChainHolder setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        return this;
    }
}
