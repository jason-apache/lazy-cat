package cool.lazy.cat.orm.core.jdbc.sql.dialect;


import cool.lazy.cat.orm.base.constant.Case;
import org.springframework.boot.jdbc.DatabaseDriver;

/**
 * @author: mahao
 * @date: 2021/4/24 16:01
 */
public interface MysqlDialect extends Dialect {

    @Override
    default DatabaseDriver getDataBaseDriver() {
        return DatabaseDriver.MYSQL;
    }

    @Override
    default String getDbFieldQuotationMarks() {
        return "`";
    }

    @Override
    default Case getDefaultCharacterCase() {
        return Case.LOWERCASE;
    }
}
