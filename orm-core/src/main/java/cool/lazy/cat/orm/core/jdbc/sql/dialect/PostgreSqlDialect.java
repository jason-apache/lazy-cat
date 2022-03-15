package cool.lazy.cat.orm.core.jdbc.sql.dialect;

import cool.lazy.cat.orm.base.constant.Case;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2022-01-19 18:08
 */
public interface PostgreSqlDialect extends Dialect {

    @Override
    default DatabaseDriver getDataBaseDriver() {
        return DatabaseDriver.POSTGRESQL;
    }

    @Override
    default String getDbFieldQuotationMarks() {
        return "\"";
    }

    @Override
    default Case getDefaultCharacterCase() {
        return Case.LOWERCASE;
    }
}
