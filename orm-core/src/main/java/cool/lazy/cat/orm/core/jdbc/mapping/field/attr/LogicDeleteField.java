package cool.lazy.cat.orm.core.jdbc.mapping.field.attr;

/**
 * @author: mahao
 * @date: 2021/10/18 12:03
 * 逻辑删除字段
 */
public interface LogicDeleteField extends PojoField {

    /**
     * @return 被删除的数据标识
     */
    String getDeleteValue();

    /**
     * @return 未被删除的数据标识
     */
    String getNormalValue();
}
