package cool.lazy.cat.orm.core.jdbc.sql;

/**
 * @author: mahao
 * @date: 2021/10/13 15:26
 */
public class ObjectNameImpl implements ObjectName {

    private final String schema;
    private final String name;

    public ObjectNameImpl(String schema, String name) {
        this.schema = schema;
        this.name = name;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public String getName() {
        return name;
    }
}
