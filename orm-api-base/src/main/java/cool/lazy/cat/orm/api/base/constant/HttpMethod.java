package cool.lazy.cat.orm.api.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-05-20 17:39
 * 复制 org.springframework.http.HttpMethod
 */
public enum HttpMethod {

    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;


    private static final Map<String, HttpMethod> MAPPINGS = new HashMap<>(16);

    static {
        for (HttpMethod httpMethod : values()) {
            MAPPINGS.put(httpMethod.name(), httpMethod);
        }
    }


    /**
     * Resolve the given method value to an {@code HttpMethod}.
     * @param method the method value as a String
     * @return the corresponding {@code HttpMethod}, or {@code null} if not found
     * @since 4.2.4
     */
    public static HttpMethod resolve(String method) {
        return (method != null ? MAPPINGS.get(method) : null);
    }


    /**
     * Determine whether this {@code HttpMethod} matches the given
     * method value.
     * @param method the method value as a String
     * @return {@code true} if it matches, {@code false} otherwise
     * @since 4.2.4
     */
    public boolean matches(String method) {
        return (this == resolve(method));
    }
}
