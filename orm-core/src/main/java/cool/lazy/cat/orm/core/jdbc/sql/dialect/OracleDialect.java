package cool.lazy.cat.orm.core.jdbc.sql.dialect;


import cool.lazy.cat.orm.core.jdbc.constant.Case;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/4/24 16:01
 */
public interface OracleDialect extends Dialect {

    @Override
    default DatabaseDriver getDataBaseDriver() {
        return DatabaseDriver.ORACLE;
    }

    @Override
    default String getDbFieldQuotationMarks() {
        return "\"";
    }

    @Override
    default Case getDefaultCharacterCase() {
        return Case.UPPERCASE;
    }
}
