package cool.lazy.cat.orm.core.jdbc.generator;

/**
 * @author: mahao
 * @date: 2021/3/20 11:35
 * 别名生成器
 */
public interface AliasNameGenerator {

    /**
     * 生成表别名
     * @param tableName 表名
     * @param index 表位于表链中的索引
     * @return 表别名
     */
    String generatorTableName(String tableName, int index);

    /**
     * 生成字段别名
     * @param fieldName 字段名
     * @param index 字段索引
     * @return 字段别名
     */
    String generatorFiledName(String fieldName, int index);
}
