package cool.lazy.cat.orm.generator.info;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/11 15:47
 */
public class TableInfo {

    private final String catalog;
    private final String schema;
    /**
     * 表名称
     */
    private final String name;

    /**
     * 表注释
     */
    private final String comment;
    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;
    /**
     * 表字段
     */
    private final List<TableFieldInfo> fields = new ArrayList<>();

    public TableInfo(String catalog, String schema, String name, String comment) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.comment = comment;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public boolean isHavePrimaryKey() {
        return havePrimaryKey;
    }

    public void setHavePrimaryKey(boolean havePrimaryKey) {
        this.havePrimaryKey = havePrimaryKey;
    }

    public List<TableFieldInfo> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "{" + "\"catalog\":\"" + catalog + '\"' + ",\"schema\":\"" + schema + '\"' + ",\"name\":\"" + name + '\"' + ",\"comment\":\"" + comment + '\"' + '}';
    }
}
