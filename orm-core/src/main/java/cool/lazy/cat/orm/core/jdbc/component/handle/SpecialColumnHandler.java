package cool.lazy.cat.orm.core.jdbc.component.handle;

import cool.lazy.cat.orm.core.jdbc.param.Param;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/11/2 10:31
 * 特殊列处理器
 */
public interface SpecialColumnHandler {

    /**
     * 增|改 处理涉及到的特殊列
     * @param param sql操作参数
     * @param mappings sql操作参数映射
     */
    void handle(Param param, List<ParameterMapping> mappings);
}
