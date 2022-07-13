package cool.lazy.cat.orm.generator.dialect.extractor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : jason.ma
 * @date : 2022/7/13 11:28
 */
abstract class AbstractRawData implements RawData {
    final Map<String, Object> raw = new LinkedHashMap<>();

    @Override
    public Map<String, Object> getRaw() {
        return raw;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : raw.entrySet()) {
            sb.append(", ").append('\"').append(entry.getKey()).append('\"').append(": ").append('\"').append(entry.getValue()).append('\"');
        }
        return sb.toString();
    }
}
