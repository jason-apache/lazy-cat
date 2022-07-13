package cool.lazy.cat.orm.generator.code;

import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.dialect.DialectRegistry;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.List;

/**
 * java代码生成器
 * @author : jason.ma
 * @date : 2022/7/11 18:08
 */
public class CodeGenerator {

    public void generate(CodeGeneratorConfig generatorConfig) {
        Dialect dialect = DialectRegistry.getInstance().get(generatorConfig.getJdbcConnectionConfig().getDatabaseType());
        List<TableInfo> tableInfoList = dialect.extractTableInfo(generatorConfig);
    }
}
