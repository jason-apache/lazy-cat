package cool.lazy.cat.orm.generator.code.naming;

import cool.lazy.cat.orm.generator.config.NamingConfig;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;

/**
 * @author : jason.ma
 * @date : 2022/7/13 12:03
 */
public interface NamingFactory {

    /**
     * 为java类命名
     * @param tableInfo 数据库表信息
     * @param namingConfig 命名配置项
     * @return 类名
     */
    String namingClass(TableInfo tableInfo, NamingConfig namingConfig);

    /**
     * 为java类属性命名
     * @param tableFieldInfo 数据库字段信息
     * @param namingConfig 命名配置项
     * @return 属性名
     */
    String namingField(TableFieldInfo tableFieldInfo, NamingConfig namingConfig);
}
