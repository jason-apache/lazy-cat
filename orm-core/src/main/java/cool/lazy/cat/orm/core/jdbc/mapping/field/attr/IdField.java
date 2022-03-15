package cool.lazy.cat.orm.core.jdbc.mapping.field.attr;

import cool.lazy.cat.orm.base.component.IdGenerator;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.ParameterizationInfo;

/**
 * @author: mahao
 * @date: 2021/10/18 11:57
 * id字段
 */
public interface IdField extends PojoField, ParameterizationInfo {

    /**
     * @return id生成器类型
     */
    Class<? extends IdGenerator> getIdGenerator();

}
