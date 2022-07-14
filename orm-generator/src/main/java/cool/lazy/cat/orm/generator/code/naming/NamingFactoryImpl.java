package cool.lazy.cat.orm.generator.code.naming;

import cool.lazy.cat.orm.generator.config.NamingConfig;
import cool.lazy.cat.orm.generator.constant.ConstantEnum;
import cool.lazy.cat.orm.generator.constant.ConstantRegistry;
import cool.lazy.cat.orm.generator.info.TableFieldInfo;
import cool.lazy.cat.orm.generator.info.TableInfo;
import cool.lazy.cat.orm.generator.info.TypeInfo;
import cool.lazy.cat.orm.generator.util.StringUtil;

/**
 * @author : jason.ma
 * @date : 2022/7/13 15:55
 */
public class NamingFactoryImpl implements NamingFactory {

    @Override
    public String namingClass(TableInfo tableInfo, NamingConfig namingConfig) {
        String prefix = namingConfig.getClassPrefix() == null ? "" : namingConfig.getClassPrefix();
        String suffix = namingConfig.getClassSuffix() == null ? "" : namingConfig.getClassSuffix();
        if (namingConfig.getNamingStyle() == NamingConfig.NamingStyle.ORIGINAL) {
            return prefix + tableInfo.getName() + suffix;
        } else {
            return prefix + StringUtil.underline2Camel(tableInfo.getName(), false) + suffix;
        }
    }

    @Override
    public String namingField(TableFieldInfo tableFieldInfo, NamingConfig namingConfig) {
        if (namingConfig.getNamingStyle() == NamingConfig.NamingStyle.ORIGINAL) {
            return tableFieldInfo.getName();
        } else {
            return StringUtil.underline2Camel(tableFieldInfo.getName(), true);
        }
    }

    @Override
    public String namingSetter(TypeInfo fieldType, String fieldName) {
        return "set" + StringUtil.lowerToUpperFirst(fieldName);
    }

    @Override
    public String namingGetter(TypeInfo fieldType, String fieldName) {
        String upperCamel = StringUtil.lowerToUpperFirst(fieldName);
        if ("boolean".equalsIgnoreCase(fieldType.getName())) {
            return ConstantRegistry.getString(ConstantEnum.BOOLEAN_GETTER_NAMING_PREFIX) + upperCamel;
        }
        return "get" + upperCamel;
    }
}
