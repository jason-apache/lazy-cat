package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.constant.Case;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;

/**
 * @author: mahao
 * @date: 2021/7/28 13:40
 */
public class DynamicNameSqlStringImpl implements SqlString, DynamicNameSqlString, InitializationRequiredSqlString {

    private final Class<?> pojoType;
    private String schema;
    private String tableName;
    private String aliasName;
    private final String quota;
    private boolean initialized;

    public DynamicNameSqlStringImpl(Class<?> pojoType, String schema, String tableName, String aliasName, String quota) {
        this.pojoType = pojoType;
        this.schema = schema;
        this.tableName = tableName;
        this.aliasName = aliasName;
        this.quota = quota;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public void setName(String name) {
        this.tableName = name;
    }

    @Override
    public String getName() {
        return tableName;
    }

    @Override
    public String getAliasName() {
        return aliasName;
    }

    @Override
    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public boolean initialized() {
        return initialized;
    }

    @Override
    public void setInitialization(boolean initialization) {
        this.initialized = initialization;
    }

    @Override
    public String getFullName() {
        String fullName = "";
        if (StringUtil.isNotEmpty(this.schema)) {
            fullName += quota + schema + quota + ".";
        }
        fullName += quota + this.getName() + quota;
        if (StringUtil.isNotEmpty(this.aliasName)) {
            fullName += " " + this.aliasName;
        }
        return fullName;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }

    @Override
    public void changeCase(Case charCase) {
        if (charCase != JdbcConstant.DEFAULT_CASE) {
            switch (charCase) {
                case UPPERCASE:
                    if (null != this.schema) {
                        this.schema = this.schema.toUpperCase();
                    }
                    if (null != this.tableName) {
                        this.tableName = this.tableName.toUpperCase();
                    }
                    if (null != this.aliasName) {
                        this.aliasName = this.aliasName.toUpperCase();
                    }
                    break;
                case LOWERCASE:
                default:
                    if (null != this.schema) {
                        this.schema = this.schema.toLowerCase();
                    }
                    if (null != this.tableName) {
                        this.tableName = this.tableName.toLowerCase();
                    }
                    if (null != this.aliasName) {
                        this.aliasName = this.aliasName.toLowerCase();
                    }
            }
        }
    }
}
