package cool.lazy.cat.orm.core.jdbc.sql.dialect;

import cool.lazy.cat.orm.base.constant.Case;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionary;
import cool.lazy.cat.orm.core.jdbc.dict.KeywordDictionaryRegistry;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.fn.FunctionSqlString;
import cool.lazy.cat.orm.core.jdbc.sql.structure.DQLSqlStructure;
import org.springframework.boot.jdbc.DatabaseDriver;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/25 19:18
 * 数据库方言
 */
public interface Dialect {

    /**
     * @return 方言数据库类型
     */
    DatabaseDriver getDataBaseDriver();

    /**
     * 处理函数
     * @param functionSqlString 函数
     */
    void handleFunctionSqlString(FunctionSqlString functionSqlString);

    /**
     * sql语句操纵数据库字段时的转义引号
     * sql可能涉及操作数据库关键字 为了避免关键字碰撞 将每一个字段都使用引号包裹转义
     * 各个数据库的引号可能都是不同的 mysql是 ` Oracle是 "
     * @return 转义引号
     */
    String getDbFieldQuotationMarks();

    /**
     * @return 数据库默认字符大写/小写
     */
    Case getDefaultCharacterCase();

    /**
     * @return 关键字字典
     */
    default KeywordDictionary getKeywordDictionary() {
        return KeywordDictionaryRegistry.getInstance(this.getDefaultCharacterCase());
    }

    /**
     * 生成分页sql语句
     * @param searchParam 查询参数
     * @param dqlSqlStructure 查询sql语句
     * @return 分页sql语句
     */
    List<SqlString> limitSql(SearchParam<?> searchParam, DQLSqlStructure dqlSqlStructure);

    /**
     * 生成count查询语句
     * @param searchParam 查询参数
     * @param dqlSqlStructure 查询sql语句
     * @return count查询语句
     */
    List<SqlString> countSql(SearchParam<?> searchParam, DQLSqlStructure dqlSqlStructure);

    /**
     * 生成查询序列当前值的sql
     * @param schema 序列所在的库
     * @param sequenceName 序列名称
     * @return 查询序列当前值的sql
     */
    List<SqlString> selectSequenceCurrentValueSql(Class<?> pojoType, String schema, String sequenceName);

    /**
     * 生成查询序列下一个值的sql
     * @param schema 序列所在的库
     * @param sequenceName 序列名称
     * @return 查询序列下一个值的sql
     */
    List<SqlString> selectSequenceNextValueSql(Class<?> pojoType, String schema, String sequenceName);
}
