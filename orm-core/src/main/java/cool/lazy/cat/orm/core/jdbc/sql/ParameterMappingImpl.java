package cool.lazy.cat.orm.core.jdbc.sql;

import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;
import cool.lazy.cat.orm.core.jdbc.sql.structure.SqlStructure;

import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/9/20 21:44
 */
public class ParameterMappingImpl implements ParameterMapping {

    private final Class<?> pojoType;
    private SqlStructure sqlStructure;
    private List<SqlSource> sqlSources;
    private Map<String, PojoField> affectedFieldMapping;

    public ParameterMappingImpl(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    public ParameterMappingImpl(Class<?> pojoType, SqlStructure sqlStructure) {
        this.pojoType = pojoType;
        this.sqlStructure = sqlStructure;
    }

    @Override
    public void setSqlStructure(SqlStructure sqlStructure) {
        this.sqlStructure = sqlStructure;
    }

    @Override
    public SqlStructure getSqlStructure() {
        return sqlStructure;
    }

    @Override
    public void setSqlSources(List<SqlSource> sqlSources) {
        this.sqlSources = sqlSources;
    }

    @Override
    public List<SqlSource> getSqlSources() {
        return this.sqlSources;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }

    @Override
    public void setAffectedFieldMapping(Map<String, PojoField> affectedFieldMapping) {
        this.affectedFieldMapping = affectedFieldMapping;
    }

    @Override
    public Map<String, PojoField> getAffectedFieldMapping() {
        return this.affectedFieldMapping;
    }
}
