package cool.lazy.cat.orm.core.jdbc.sql.structure;

import cool.lazy.cat.orm.core.jdbc.sql.string.SqlString;
import cool.lazy.cat.orm.core.jdbc.sql.string.keyword.KeywordSqlString;

import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/10/13 15:33
 * 行为描述符 描述sql直接操作对象
 */
public interface BehaviorDescriptor {

    /**
     * @return 操作对象关键字
     */
    KeywordSqlString getKeyword();

    /**
     * @return sql内容
     */
    Collection<SqlString> getContent();
}
