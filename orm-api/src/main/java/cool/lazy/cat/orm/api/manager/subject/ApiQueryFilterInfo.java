package cool.lazy.cat.orm.api.manager.subject;

import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;

import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-02-05 15:59
 */
public class ApiQueryFilterInfo extends AbstractParameterizationInfo {

    protected final Class<? extends ConditionType> queryFilterType;

    public ApiQueryFilterInfo(ApiQueryFilter apiQueryFilter) {
        this(apiQueryFilter.value());
        super.initParameter(apiQueryFilter.parameters());
    }

    public ApiQueryFilterInfo(Class<? extends ConditionType> queryFilterType) {
        this.queryFilterType = queryFilterType;
    }

    public Class<? extends ConditionType> getQueryFilterType() {
        return queryFilterType;
    }

    public void setParameterMapping(Map<String, String> parameterMapping) {
        super.parameterMapping = parameterMapping;
    }

    @Override
    public String toString() {
        return queryFilterType.toString();
    }
}
