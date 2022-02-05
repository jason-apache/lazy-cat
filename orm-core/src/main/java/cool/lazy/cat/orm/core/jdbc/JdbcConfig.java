package cool.lazy.cat.orm.core.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/14 12:10
 */
@ConfigurationProperties(prefix = "cool.lazy-cat.jdbc")
public class JdbcConfig {

    /**
     * 从数据库映射对象时是否复用对象
     */
    private boolean multiplexObject = true;
    /**
     * 扫描pojo类路径, 配置此参数将忽略@PojoScan注解
     */
    private List<String> pojoScanBasePackages;
    /**
     * 是否开启service bean注入 调整此选项可能引起某些功能无法正常工作
     */
    private boolean enableServiceRegistry = true;

    public boolean isMultiplexObject() {
        return multiplexObject;
    }

    public void setMultiplexObject(boolean multiplexObject) {
        this.multiplexObject = multiplexObject;
    }

    public List<String> getPojoScanBasePackages() {
        return pojoScanBasePackages;
    }

    public void setPojoScanBasePackages(List<String> pojoScanBasePackages) {
        this.pojoScanBasePackages = pojoScanBasePackages;
    }

    public boolean isEnableServiceRegistry() {
        return enableServiceRegistry;
    }

    public void setEnableServiceRegistry(boolean enableServiceRegistry) {
        this.enableServiceRegistry = enableServiceRegistry;
    }
}
