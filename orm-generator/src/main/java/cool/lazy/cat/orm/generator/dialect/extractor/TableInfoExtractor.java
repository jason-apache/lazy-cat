package cool.lazy.cat.orm.generator.dialect.extractor;

import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.List;

/**
 * 表信息提取器
 * @author : jason.ma
 * @date : 2022/7/13 11:22
 */
public interface TableInfoExtractor {

    /**
     * @param generatorConfig 配置
     * @return 表信息
     */
    List<TableInfo> extractTableInfo(CodeGeneratorConfig generatorConfig);

    /**
     * @return 数据库方言
     */
    Dialect getDialect();
}
