package cool.lazy.cat.orm.core.jdbc.sql.interceptor;

import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;

/**
 * @author: mahao
 * @date: 2021/7/22 17:47
 */
public interface SqlInterceptor {

    /**
     * 是否满足拦截条件
     * @param sqlParameterMapping sql参数映射
     * @return 是否拦截
     */
    boolean intercept(SqlParameterMapping sqlParameterMapping);

    /**
     * 在sql执行之前进行拦截操作
     * @param sqlParameterMapping sql参数映射
     */
    void preExecute(SqlParameterMapping sqlParameterMapping);
}
