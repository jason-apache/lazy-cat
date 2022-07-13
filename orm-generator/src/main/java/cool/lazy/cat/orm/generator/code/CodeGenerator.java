package cool.lazy.cat.orm.generator.code;

import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
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

    public void generate(CodeGeneratorConfig generatorConfig) {
        // 初始化数据库连接
        ConnectionManager.initConnection(generatorConfig.getJdbcConnectionConfig());
        Dialect dialect = DialectRegistry.getInstance().get(generatorConfig.getJdbcConnectionConfig().getDatabaseType());
        List<TableInfo> tableInfoList = dialect.extractTableInfo(generatorConfig.getScanningConfig());
    }
}
