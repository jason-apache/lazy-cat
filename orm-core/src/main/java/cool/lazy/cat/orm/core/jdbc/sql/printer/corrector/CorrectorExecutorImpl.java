package cool.lazy.cat.orm.core.jdbc.sql.printer.corrector;

import cool.lazy.cat.orm.core.jdbc.exception.executor.InsolubleCauseException;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.printer.cause.Cause;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: mahao
 * @date: 2021/7/29 11:22
 */
public class CorrectorExecutorImpl implements CorrectorExecutor {

    protected final List<Corrector> correctorList;
    protected final Map<CorrectorKey, Corrector> causeCache;

    public CorrectorExecutorImpl(List<Corrector> correctorList) {
        this.correctorList = correctorList;
        this.causeCache = new ConcurrentHashMap<>(correctorList.size());
    }

    protected Corrector lookupCorrector(Class<? extends SqlType> sqlType, Cause cause) {
        CorrectorKey key = new CorrectorKey(sqlType, cause.getClass());
        Corrector c = causeCache.get(key);
        if (null != c) {
            return c;
        }
        for (Corrector corrector : correctorList) {
            if (corrector.support(sqlType, cause)) {
                causeCache.put(key, corrector);
                return corrector;
            }
        }
        throw new InsolubleCauseException("无法解决的问题: " + cause.getClass().getName());
    }

    @Override
    public void correcting(SqlString sqlString, SqlParameterMapping sqlParameterMapping) {
        Corrector preCorrector = null;
        SqlString prePaper = null;
        while (sqlString.paperJam()) {
            Cause cause = sqlString.cause();
            if (null == cause) {
                break;
            }
            Corrector corrector = this.lookupCorrector(sqlParameterMapping.getType(), cause);
            // 为了避免无限循环执行 原因与修正器与之前相同 则抛出异常
            if (corrector == preCorrector && cause == prePaper) {
                throw new InsolubleCauseException("无法解决的问题: " + cause.getClass().getName());
            }
            corrector.fix(cause, sqlString, sqlParameterMapping);
            preCorrector = corrector;
            prePaper = cause.getSqlString();
        }
    }

    private static final class CorrectorKey {
        final Class<? extends SqlType> sqlType;
        final Class<? extends Cause> causeType;

        private CorrectorKey(Class<? extends SqlType> sqlType, Class<? extends Cause> causeType) {
            this.sqlType = sqlType;
            this.causeType = causeType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CorrectorKey that = (CorrectorKey) o;
            if (sqlType != that.sqlType) {
                return false;
            }
            return Objects.equals(causeType, that.causeType);
        }

        @Override
        public int hashCode() {
            int result = sqlType != null ? sqlType.hashCode() : 0;
            result = 31 * result + (causeType != null ? causeType.hashCode() : 0);
            return result;
        }
    }
}
