package cool.lazy.cat.orm.core.jdbc.sql.printer;

import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.corrector.CorrectorExecutor;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: mahao
 * @date: 2021/7/16 14:01
 */
public abstract class AbstractSqlPrinter implements SqlPrinter {

    protected final CorrectorExecutor correctorExecutor;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected AbstractSqlPrinter(CorrectorExecutor correctorExecutor) {
        this.correctorExecutor = correctorExecutor;
    }

    protected void renderSqlString(SqlString sqlString, SqlParameterMapping sqlParameterMapping) {
        this.renderSqlString(sqlString, sqlParameterMapping, sqlParameterMapping.getCurrentlyProcessed());
    }

    protected void renderSqlString(SqlString sqlString, SqlParameterMapping sqlParameterMapping, ParameterMapping parameterMapping) {
        correctorExecutor.correcting(sqlString, sqlParameterMapping);
        parameterMapping.getSqlStructure().addSqlString(sqlString);
    }
}
