package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/8/23 10:22
 */
public class TableNodeImpl implements TableNode {

    private FieldDescriptor idDescriptor;
    private final PathDescriptor pathDescriptor;
    private final String schema;
    private final String tableName;
    private final Class<?> pojoType;
    private final TableNode belongPojoTable;
    private final PojoMapping pojoMapping;
    private String tableAliasName;
    private List<TableNode> childrenList;
    private Map<String, FieldDescriptor> fieldMapping;
    private int index;

    public TableNodeImpl(PathDescriptor pathDescriptor, String schema, String tableName, Class<?> pojoType, TableNode belongPojoTable, PojoMapping pojoMapping) {
        this.pathDescriptor = pathDescriptor;
        this.schema = schema;
        this.tableName = tableName;
        this.pojoType = pojoType;
        this.belongPojoTable = belongPojoTable;
        this.pojoMapping = pojoMapping;
    }

    @Override
    public IdField getId() {
        return (IdField) this.idDescriptor.getRealField();
    }

    @Override
    public FieldDescriptor getIdDescriptor() {
        return idDescriptor;
    }

    @Override
    public FieldDescriptor setIdDescriptor(FieldDescriptor idDescriptor) {
        return this.idDescriptor = idDescriptor;
    }

    @Override
    public PathDescriptor getPath() {
        return pathDescriptor;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public String tableName() {
        return this.tableName;
    }

    @Override
    public String tableAliasName() {
        return this.tableAliasName;
    }

    @Override
    public void setTableAliasName(String tableAliasName) {
        this.tableAliasName = tableAliasName;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public TableNode getBelongPojoTable() {
        return this.belongPojoTable;
    }

    @Override
    public void setChildren(List<TableNode> children) {
        this.childrenList = children;
    }

    @Override
    public List<TableNode> getChildren() {
        return childrenList;
    }

    @Override
    public void setFieldMapping(Map<String, FieldDescriptor> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    @Override
    public Map<String, FieldDescriptor> getFieldMapping() {
        return fieldMapping;
    }

    @Override
    public PojoMapping getPojoMapping() {
        return this.pojoMapping;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getPath().getFullPath();
    }
}