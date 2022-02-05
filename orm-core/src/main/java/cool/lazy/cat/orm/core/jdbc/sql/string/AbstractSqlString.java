package cool.lazy.cat.orm.core.jdbc.sql.string;

import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.constant.Case;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;

/**
 * @author: mahao
 * @date: 2021/7/20 12:01
 */
public abstract class AbstractSqlString implements SqlString {

    protected String val;

    public String getVal() {
        return val;
    }

    protected AbstractSqlString(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public void changeCase(Case charCase) {
        // lazy-cat默认小写
        if (charCase != JdbcConstant.DEFAULT_CASE) {
            if (StringUtil.isNotEmpty(this.val)) {
                switch (charCase) {
                    case UPPERCASE:
                        this.val = this.val.toUpperCase();
                        break;
                    case LOWERCASE:
                    default:
                        this.val = this.val.toLowerCase();
                }
            }
        }
    }
}
