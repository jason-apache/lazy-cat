package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.jdbc.constant.Case;

/**
 * @author: mahao
 * @date: 2021/7/13 17:05
 */
public class PojoFieldSqlString extends AbstractSqlString implements FieldSqlString, NormalSqlString {

    /**
     * 是否不可变更大小写
     */
    private final boolean immutable;

    public PojoFieldSqlString(String val, boolean immutable) {
        super(val);
        this.immutable = immutable;
    }

    @Override
    public void changeCase(Case charCase) {
        if (this.immutable) {
            return;
        }
        super.changeCase(charCase);
    }
}
