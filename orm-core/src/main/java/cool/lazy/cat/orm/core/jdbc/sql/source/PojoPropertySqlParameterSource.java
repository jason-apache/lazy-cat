package cool.lazy.cat.orm.core.jdbc.sql.source;

import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/29 20:38
 */
public class PojoPropertySqlParameterSource extends AbstractSqlParameterSource implements PojoSqlSource, SqlParameterSource {

    protected final Object pojo;
    protected Map<String, Object> payload;
    private boolean modified;

    public PojoPropertySqlParameterSource(Object pojo, Map<String, ?> payload) {
        this.pojo = pojo;
        this.payload = Caster.cast(payload);
    }

    public PojoPropertySqlParameterSource(Object pojo) {
        this.pojo = pojo;
        this.payload = Caster.cast(PojoTableManager.getDefaultInstance().getByPojoType(pojo.getClass()).getTableInfo().getFieldInfoMap());
    }

    @Override
    public boolean hasValue(String paramName) {
        // sql根据pojo字段生成，理论上此处无需判断，始终返回true，以提高效率
        // return payload.containsKey(paramName);
        return true;
    }

    protected Object tryGet(String fieldName) {
        Object val = payload.get(fieldName);
        if (val instanceof PojoField) {
            return ReflectUtil.invokeGetter(((PojoField) val).getGetter(), pojo);
        }
        return val;
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        return this.tryGet(paramName);
    }

    @Override
    public int getSqlType(String paramName) {
        Object val = this.tryGet(paramName);
        Class<?> javaType = val == null ? null : val.getClass();
        return StatementCreatorUtils.javaTypeToSqlParameterType(javaType);
    }

    @Override
    public String[] getParameterNames() {
        return payload.keySet().toArray(new String[0]);
    }

    @Override
    public SqlSource set(String key, Object v) {
        if (!modified) {
            this.payload = new LinkedHashMap<>(payload);
        }
        this.payload.put(key, v);
        modified = true;
        return this;
    }

    @Override
    public Object getPojo() {
        return this.pojo;
    }

    @Override
    public Class<?> getPojoType() {
        return this.pojo.getClass();
    }
}
