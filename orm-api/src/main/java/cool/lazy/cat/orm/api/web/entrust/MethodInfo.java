package cool.lazy.cat.orm.api.web.entrust;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author: mahao
 * @date: 2022-01-28 16:35
 */
public interface MethodInfo {

    /**
     * @return java类方法
     */
    Method getMethod();

    /**
     * @return 方法参数列表
     */
    MethodParameter[] getParameters();
}
