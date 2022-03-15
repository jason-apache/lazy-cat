package cool.lazy.cat.orm.core.jdbc.sql.structure;

import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.base.constant.Case;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.WhereSqlString;
import cool.lazy.cat.orm.core.jdbc.util.SqlStringJoinerHelper;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/13 13:48
 * sql结构
 */
public interface SqlStructure {

    void setBehaviorDescriptor(BehaviorDescriptor behaviorDescriptor);

    /**
     * @return 行为描述符对象
     */
    BehaviorDescriptor getBehaviorDescriptor();

    /**
     * 追加sql
     * @param sql sql
     */
    void addSqlString(SqlString sql);

    /**
     * 插入sql
     * @param index 指定位置
     * @param sqlString sql
     */
    void insertSqlString(int index, SqlString sqlString);

    /**
     * @return sql内容
     */
    Collection<SqlString> getSqlStrings();

    /**
     * @return sql条件字符
     */
    WhereSqlString<?> getWhere();

    void setWhere(WhereSqlString<?> where);

    /**
     * 重排序
     */
    void reOrderWhere();

    /**
     * @return 转换为可执行的sql语句
     */
    default String toSqlString() {
        return this.toSqlString(JdbcOperationSupport.getDialect().getDefaultCharacterCase());
    }

    /**
     * @param charCase 字符大小写
     * @return 转换为可执行的sql语句
     */
    default String toSqlString(Case charCase) {
        return SqlStringJoinerHelper.join(this.getSqlStrings(), charCase);
    }

    /**
     * @return sql条件参数
     */
    SqlCondition getCondition();

    void setCondition(SqlCondition condition);
}
