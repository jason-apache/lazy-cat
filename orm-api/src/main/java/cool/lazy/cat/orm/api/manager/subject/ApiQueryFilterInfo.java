package cool.lazy.cat.orm.api.manager.subject;

import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.mapping.parameter.AbstractParameterizationInfo;

/**
 * @author: mahao
 * @date: 2022-02-05 15:59
 */
public class ApiQueryFilterInfo extends AbstractParameterizationInfo {

    protected final Class<? extends ConditionType> queryFilterType;

    public ApiQueryFilterInfo(ApiQueryFilter apiQueryFilter) {
        super.initParameter(apiQueryFilter.parameters());
        this.queryFilterType = apiQueryFilter.value();
    }

    public Class<? extends ConditionType> getQueryFilterType() {
        return queryFilterType;
    }
}
