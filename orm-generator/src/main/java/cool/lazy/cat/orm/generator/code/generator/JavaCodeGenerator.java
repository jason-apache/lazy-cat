package cool.lazy.cat.orm.generator.code.generator;

import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
import cool.lazy.cat.orm.generator.dialect.Dialect;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.List;

/**
 * @author : jason.ma
 * @date : 2022/7/13 14:15
 */
public interface JavaCodeGenerator {

    /**
     * 根据表信息生成java文件
     * @param tableInfoList 表信息
     * @param dialect 数据库方言
     * @param codeGeneratorConfig 代码生成配置
     */
    void generate(List<TableInfo> tableInfoList, Dialect dialect, CodeGeneratorConfig codeGeneratorConfig);
}
