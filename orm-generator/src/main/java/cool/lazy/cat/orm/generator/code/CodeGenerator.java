package cool.lazy.cat.orm.generator.code;

import cool.lazy.cat.orm.generator.BeanUtil;
import cool.lazy.cat.orm.generator.code.generator.JavaCodeGenerator;
import cool.lazy.cat.orm.generator.config.CodeStyleConfig;
import cool.lazy.cat.orm.generator.config.GlobalConfig;
import cool.lazy.cat.orm.generator.constant.ConstantEnum;
import cool.lazy.cat.orm.generator.constant.ConstantRegistry;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.dialect.DialectRegistry;
import cool.lazy.cat.orm.generator.info.TableInfo;
import cool.lazy.cat.orm.generator.jdbc.ConnectionManager;

import java.util.List;

/**
 * java代码生成器
 * @author : jason.ma
 * @date : 2022/7/11 18:08
 */
public class CodeGenerator {

    protected void init(GlobalConfig globalConfig) {
        // 初始化系统常量
        this.initSystemConstant(globalConfig);
        this.initBean(globalConfig);
        // 初始化数据库连接
        ConnectionManager.initConnection(globalConfig.getJdbcConnectionConfig());
    }

    protected void initSystemConstant(GlobalConfig globalConfig) {
        CodeStyleConfig codeStyleConfig = globalConfig.getCodeGeneratorConfig().getCodeStyleConfig();
        if (null != codeStyleConfig.getIndent()) {
            ConstantRegistry.set(ConstantEnum.INDENT, codeStyleConfig.getIndent());
        }
        if (null != codeStyleConfig.getLineSeparator()) {
            ConstantRegistry.set(ConstantEnum.LINE_SEPARATOR, codeStyleConfig.getLineSeparator());
        }
        if (null != codeStyleConfig.getBooleanGetterPrefix()) {
            ConstantRegistry.set(ConstantEnum.BOOLEAN_GETTER_NAMING_PREFIX, codeStyleConfig.getBooleanGetterPrefix());
        }
    }

    protected void initBean(GlobalConfig globalConfig) {
        // todo
    }

    public void generate(GlobalConfig globalConfig) {
        this.init(globalConfig);
        Dialect dialect = DialectRegistry.getInstance().get(globalConfig.getJdbcConnectionConfig().getDatabaseType());
        List<TableInfo> tableInfoList = dialect.extractTableInfo(globalConfig.getScanningConfig());
        BeanUtil.getBeanInstance(JavaCodeGenerator.class).generate(tableInfoList, dialect, globalConfig.getCodeGeneratorConfig());
    }
}
