package cool.lazy.cat.orm.core.jdbc.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/23 10:31
 */
public class MetaInfoImpl implements MetaInfo {

    private final Map<String, Object> info = new HashMap<>();

    @Override
    public Object get(String name) {
        return info.get(name);
    }

    @Override
    public void set(String name, Object val) {
        info.put(name, val);
    }
}
