package cool.lazy.cat.orm.generator.code.naming;

import cool.lazy.cat.orm.generator.config.NamingConfig;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;
import cool.lazy.cat.orm.generator.info.TypeInfo;

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

    /**
     * @param fieldType 字段信息
     * @param fieldName 字段名称
     * @return setter方法名
     */
    String namingSetter(TypeInfo fieldType, String fieldName);

    /**
     * @param fieldType 字段信息
     * @param fieldName 字段名称
     * @return getter方法名
     */
    String namingGetter(TypeInfo fieldType, String fieldName);
}
