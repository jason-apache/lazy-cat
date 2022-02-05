package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.jdbc.mapping.Column;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2021/8/18 16:06
 */
public class FieldDescriptorImpl implements FieldDescriptor {

    private final PathDescriptor path;
    private final PojoField field;
    private final TableNode tableNode;
    private final int moldIndex;
    private int columnIndex;
    private boolean ignored;
    private String aliasName;

    public FieldDescriptorImpl(PathDescriptor path, PojoField field, TableNode tableNode, int moldIndex) {
        this.path = path;
        this.field = field;
        this.tableNode = tableNode;
        this.moldIndex = moldIndex;
    }

    @Override
    public PojoField getRealField() {
        return field;
    }

    @Override
    public String getJavaFieldName() {
        return this.field.getJavaFieldName();
    }

    @Override
    public String getDbFieldName() {
        return this.field.getDbFieldName();
    }

    @Override
    public boolean specified() {
        return this.field.specified();
    }

    @Override
    public Method getGetter() {
        return this.field.getGetter();
    }

    @Override
    public Method getSetter() {
        return this.field.getSetter();
    }

    @Override
    public PathDescriptor getPath() {
        return this.path;
    }

    @Override
    public TableNode getTableNode() {
        return this.tableNode;
    }

    @Override
    public int getMoldIndex() {
        return moldIndex;
    }

    @Override
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public boolean isIgnored() {
        return ignored;
    }

    @Override
    public boolean forced() {
        return this.field instanceof IdField || field.isForeignKey();
    }

    @Override
    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    @Override
    public String toString() {
        return this.getPath().getFullPath();
    }

    @Override
    public String getAliasName() {
        return this.aliasName;
    }

    @Override
    public boolean havingValidator() {
        return this.field.havingValidator();
    }

    @Override
    public boolean havingTypeConverter() {
        return this.field.havingTypeConverter();
    }

    @Override
    public boolean insertable() {
        return this.field.insertable();
    }

    @Override
    public boolean updatable() {
        return this.field.updatable();
    }

    @Override
    public Class<?> getJavaType() {
        return this.field.getJavaType();
    }

    @Override
    public Column getColumn() {
        return this.field.getColumn();
    }

    @Override
    public boolean isForeignKey() {
        return this.field.isForeignKey();
    }

    @Override
    public void setForeignKey(boolean foreignKey) {
    }

    @Override
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}
