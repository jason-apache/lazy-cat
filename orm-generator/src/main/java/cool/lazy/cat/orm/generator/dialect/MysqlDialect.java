package cool.lazy.cat.orm.generator.dialect;

import cool.lazy.cat.orm.generator.constant.Case;
import cool.lazy.cat.orm.generator.constant.DatabaseType;
import cool.lazy.cat.orm.generator.dialect.extractor.AbstractTableInfoExtractor;
import cool.lazy.cat.orm.generator.dialect.extractor.TableInfoExtractor;
import cool.lazy.cat.orm.generator.dialect.keyword.KeywordMap;
import cool.lazy.cat.orm.generator.dialect.keyword.MysqlKeywordMap;

/**
 * @author : jason.ma
 * @date : 2022/7/11 19:07
 */
public class MysqlDialect implements Dialect {

    protected KeywordMap keywordMap = new MysqlKeywordMap();
    protected TableInfoExtractor tableInfoExtractor = new SimpleMysqlTableInfoExtractorImpl(this);

    @Override
    public DatabaseType getDbType() {
        return DatabaseType.MYSQL;
    }

    @Override
    public String getDbFieldQuotationMarks() {
        return "`";
    }

    @Override
    public Case getDefaultCharacterCase() {
        return Case.LOWERCASE;
    }

    @Override
    public KeywordMap getKeywordMap() {
        return this.keywordMap;
    }

    @Override
    public TableInfoExtractor getTableInfoExtractor() {
        return tableInfoExtractor;
    }

    protected static class SimpleMysqlTableInfoExtractorImpl extends AbstractTableInfoExtractor implements TableInfoExtractor {
        public SimpleMysqlTableInfoExtractorImpl(Dialect dialect) {
            super(dialect);
        }
    }
}
