package cool.lazy.cat.orm.core.jdbc.sql.source;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/20 11:14
 */
public class MapSqlSource extends MapSqlParameterSource implements SqlSource {

    protected final Class<?> pojoType;

    public MapSqlSource(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    public MapSqlSource(Class<?> pojoType, String k, Object v) {
        this.pojoType = pojoType;
        super.addValue(k, v);
    }

    public MapSqlSource(Class<?> pojoType, Map<String, ?> values) {
        super(values);
        this.pojoType = pojoType;
    }

    @Override
    public SqlSource set(String key, Object v) {
        super.addValue(key, v);
        return this;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }
}
