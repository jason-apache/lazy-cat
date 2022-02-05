package cool.lazy.cat.orm.core.jdbc.component;

import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;

/**
 * @author: mahao
 * @date: 2021/11/3 11:00
 */
public interface SpecialColumn extends CommonComponent {

    /**
     * 执行一段特殊逻辑
     * @param sqlSource sql参数
     * @param pojoField 字段信息
     */
    void process(SqlSource sqlSource, PojoField pojoField);
}
