package cool.lazy.cat.orm.core.jdbc.sql.string;

/**
 * @author: mahao
 * @date: 2021/10/12 19:08
 * 表示一个内容具有开闭符号
 */
public interface SwitchSymbolSqlString extends SqlString{

    /**
     * @return 开启符号
     */
    String getOpenOperator();

    /**
     * @return 关闭符号
     */
    String getCloseOperator();
}
