package cool.lazy.cat.orm.core.jdbc.sql.source;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;

/**
 * @author: mahao
 * @date: 2021/9/15 14:18
 */
public class EmptySqlSource extends EmptySqlParameterSource implements SqlSource {

    private final Class<?> pojoType;

    public EmptySqlSource(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    @Override
    public SqlSource set(String key, Object v) {
        return this;
    }

    @Override
    public Class<?> getPojoType() {
        return pojoType;
    }
}
