package cool.lazy.cat.orm.core.jdbc.component.handle;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.component.CommonComponent;
import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.component.validator.Validator;
import cool.lazy.cat.orm.core.jdbc.mapping.Column;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.param.Param;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.provider.SpecialColumnProvider;
import cool.lazy.cat.orm.core.jdbc.sql.ParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.source.SqlSource;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/11/3 11:27
 */
public class SpecialColumnHandlerImpl implements SpecialColumnHandler {

    protected final SpecialColumnProvider specialColumnProvider;

    public SpecialColumnHandlerImpl(SpecialColumnProvider specialColumnProvider) {
        this.specialColumnProvider = specialColumnProvider;
    }

    @Override
    public void handle(Param param, List<ParameterMapping> mappings) {
        if (param instanceof SearchParam || CollectionUtil.isEmpty(mappings)) {
            return;
        }
        Set<Class<? extends CommonComponent>> excludeComponents = CollectionUtil.isEmpty(param.getExcludeComponents()) ? Collections.emptySet() : param.getExcludeComponents();
        for (ParameterMapping mapping : mappings) {
            if (CollectionUtil.isEmpty(mapping.getSqlSources()) || CollectionUtil.isEmpty(mapping.getAffectedFieldMapping())) {
                return;
            }
            for (SqlSource sqlSource : mapping.getSqlSources()) {
                for (PojoField pojoField : mapping.getAffectedFieldMapping().values()) {
                    Column column = pojoField.getColumn();
                    if (column.havingValidator()) {
                        if (!excludeComponents.contains(column.getValidatorInfo().getValidator())) {
                            Validator validator = specialColumnProvider.provider(column.getValidatorInfo().getValidator());
                            validator.process(sqlSource, pojoField);
                        }
                    }
                    if (column.havingTypeConverter()) {
                        if (!excludeComponents.contains(column.getTypeConverter())) {
                            TypeConverter typeConverter = specialColumnProvider.provider(column.getTypeConverter());
                            typeConverter.process(sqlSource, pojoField);
                        }
                    }
                }
            }
        }
    }

}
