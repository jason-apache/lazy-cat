package cool.lazy.cat.orm.core.jdbc.sql;

import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.param.operation.DataOperationDescriptor;
import cool.lazy.cat.orm.core.jdbc.param.Param;
import cool.lazy.cat.orm.core.jdbc.sql.type.SqlType;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/7/16 10:31
 * sql参数载体
 * 参与从sql打印到交互数据库返回数据库数据全过程
 */
public interface SqlParameterMapping {

    /**
     * @return sql类型
     */
    Class<? extends SqlType> getType();

    /**
     * @return sql参数映射
     */
    List<ParameterMapping> getParameterMappings();

    /**
     * @return 当前处理中的sql参数映射(最后一次添加的sql参数映射)
     */
    default ParameterMapping getCurrentlyProcessed() {
        List<ParameterMapping> mappings = this.getParameterMappings();
        if (CollectionUtil.isNotEmpty(mappings)) {
            return mappings.get(mappings.size() -1);
        }
        return null;
    }

    void setParameterMappings(List<ParameterMapping> mappings);

    /**
     * @return 入参
     */
    Param getParam();

    /**
     * @return 构建的数据操作描述对象
     */
    DataOperationDescriptor getDataOperationDescriptor();

    /**
     * @return 元信息
     */
    MetaInfo getMetaInfo();

    void setMetaInfo(MetaInfo metaInfo);
}
