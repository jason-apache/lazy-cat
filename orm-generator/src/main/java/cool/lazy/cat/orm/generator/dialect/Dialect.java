package cool.lazy.cat.orm.generator.dialect;

import cool.lazy.cat.orm.generator.config.CodeGeneratorConfig;
import cool.lazy.cat.orm.generator.constant.Case;
import cool.lazy.cat.orm.generator.constant.DatabaseType;
import cool.lazy.cat.orm.generator.dialect.extractor.TableInfoExtractor;
import cool.lazy.cat.orm.generator.dialect.keyword.KeywordMap;
import cool.lazy.cat.orm.generator.info.TableInfo;

import java.util.List;

/**
 * 数据库方言
 * @author jason.ma
 * @date 2022/7/8 18:39
 */
public interface Dialect {

    /**
     * @return 数据库类型
     */
    DatabaseType getDbType();

    /**
     * <p>sql语句操纵数据库字段时的转义引号</p>
     * <p>sql可能涉及操作数据库关键字 为了避免关键字碰撞 将每一个字段都使用引号包裹转义</p>
     * <p>各个数据库的引号可能都是不同的 mysql是 ` Oracle是 "</p>
     * @return 转义引号
     */
    String getDbFieldQuotationMarks();

    /**
     * @return 数据库默认字符大写/小写
     */
    Case getDefaultCharacterCase();

    /**
     * @return 关键字映射表
     */
    KeywordMap getKeywordMap();

    /**
     * @return 数据库表信息提取器
     */
    TableInfoExtractor getTableInfoExtractor();

    default List<TableInfo> extractTableInfo(CodeGeneratorConfig generatorConfig) {
        return this.getTableInfoExtractor().extractTableInfo(generatorConfig);
    }

    /**
     * 判断表字段是否为主键
     * @param valueDef 列主键值项定义
     * @return 列是否为主键
     */
    default boolean isAutoIncrement(String valueDef) {
        return "yes".equalsIgnoreCase(valueDef);
    }
}
