package cool.lazy.cat.orm.core.jdbc.sql.structure;

/**
 * @author: mahao
 * @date: 2021/10/13 14:01
 * 数据操纵语言结构
 */
public interface DMLSqlStructure extends SqlStructure{

    @Override
    default void reOrderWhere() {}

}
