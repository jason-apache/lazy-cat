package cool.lazy.cat.orm.api.web.entrust;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/4/24 21:14
 */
public class ErrorData {

    private final Map<String, Object> data = new HashMap<>();

    public ErrorData(String status, String code, String msg) {
        data.put("status", status);
        data.put("code", code);
        data.put("message", msg);
    }

    public Map<String, Object> getData() {
        return data;
    }
}
