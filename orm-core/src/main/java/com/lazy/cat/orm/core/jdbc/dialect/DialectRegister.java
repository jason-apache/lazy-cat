package com.lazy.cat.orm.core.jdbc.dialect;

import com.lazy.cat.orm.core.base.util.CollectionUtil;
import com.lazy.cat.orm.core.jdbc.JdbcConfig;
import com.lazy.cat.orm.core.jdbc.exception.CannotFindDialectException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 19:45
 * 数据库方言注册器，没有显示定义方言时，提供一个默认的方言实现
 */
public class DialectRegister {

    @Autowired
    protected JdbcConfig jdbcConfig;
    @Autowired
    protected SimpleMysqlDialect simpleMysqlDialect;
    @Autowired
    protected SimpleOracleDialect simpleOracleDialect;
    private Dialect dialect;
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private void initDialect(List<Dialect> dialectList) {
        if (CollectionUtil.isEmpty(dialectList)) {
            return;
        }
        Class<?> dialectClass = jdbcConfig.getDialectClass();
        if (null == dialectClass) {
            // 尝试从数据库驱动包辨别数据库方言
            Class<?> driverClass = jdbcConfig.getDriverClass();
            if (null != driverClass) {
                String driverName = driverClass.getName().toLowerCase();
                if (driverName.contains("mysql")) {
                    this.dialect = simpleMysqlDialect;
                } else if (driverName.contains("oracle")) {
                    this.dialect = simpleOracleDialect;
                }
            }
        } else {
            if (Dialect.class.isAssignableFrom(dialectClass)) {
                // 从上下文中获取匹配的方言实现类
                Dialect dialect = dialectList.stream().filter(d -> d.getClass() == dialectClass).findFirst().orElse(null);
                if (null == dialect) {
                    throw new CannotFindDialectException("无法加载方言：" + dialectClass.getName());
                }
                this.dialect = dialect;
            } else {
                throw new CannotFindDialectException("无法加载方言：" + dialectClass.getName() + "，不是一个实现类");
            }
        }
        if (null != this.getDialect()) {
            logger.info("load dialect: " + this.dialect.getClass().getName());
        }
    }

    public Dialect getDialect() {
        return dialect;
    }
}
