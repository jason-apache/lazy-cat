package cool.lazy.cat.orm.core.jdbc.provider.impl;

import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationHolder;
import cool.lazy.cat.orm.core.jdbc.datasource.operation.JdbcOperationSupport;
import cool.lazy.cat.orm.core.jdbc.analyzer.RowAggregator;
import cool.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import cool.lazy.cat.orm.core.jdbc.exception.RowMappingException;
import cool.lazy.cat.orm.core.jdbc.exception.TypeConvertException;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldDescriptor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.TableNode;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.provider.ResultSetExtractorProvider;
import cool.lazy.cat.orm.core.jdbc.provider.SpecialColumnProvider;
import cool.lazy.cat.orm.core.jdbc.sql.SqlParameterMapping;
import cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/28 19:08
 */
public class FastMappingResultSetExtractorProvider implements ResultSetExtractorProvider {

    protected final RowAggregator rowAggregator;
    protected final PojoTableManager pojoTableManager;
    protected final SpecialColumnProvider specialColumnProvider;
    protected final Log logger = LogFactory.getLog(getClass());

    public FastMappingResultSetExtractorProvider(RowAggregator rowAggregator, PojoTableManager pojoTableManager, SpecialColumnProvider specialColumnProvider) {
        this.rowAggregator = rowAggregator;
        this.pojoTableManager = pojoTableManager;
        this.specialColumnProvider = specialColumnProvider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ResultSetExtractor<List<T>> provider(SqlParameterMapping sqlParameterMapping) {
        return resultSet -> {
            int rowNum = 0;
            JdbcOperationHolder jdbcOperationHolder = JdbcOperationSupport.getAndCheck();
            Dialect dialect = jdbcOperationHolder.getDialect();
            SearchParam<T> searchParam = Caster.cast(sqlParameterMapping.getParam());
            FieldAccessor fieldAccessor = searchParam.getFieldAccessor();
            List<Object> result = new ArrayList<>(searchParam.getContainerSize());
            TableInfo tableInfo = searchParam.getTableInfo();
            boolean nested = fieldAccessor.nested();
            try {
                while (resultSet.next()) {
                    Object[] instance = this.mappingRow(dialect, fieldAccessor, resultSet);
                    if (instance == null) {
                        continue;
                    }
                    if (nested) {
                        result.add(instance);
                    } else {
                        result.add(instance[0]);
                    }
                    rowNum ++;
                }
            } catch (Exception e) {
                throw new RowMappingException("行映射异常：" + tableInfo.getPojoType().getName() + "，rowNumber："+ rowNum, e);
            }
            if (nested) {
                // 合并一对多映射
                return (List<T>) rowAggregator.mergeRow(tableInfo, fieldAccessor, Caster.cast(result));
            }
            return (List<T>) result;
        };
    }

    protected Object[] mappingRow(Dialect dialect, FieldAccessor fieldAccessor, ResultSet resultSet) throws SQLException {
        Collection<TableNode> tableNodes = fieldAccessor.getTableNodeMapping().values();
        Object[] instances = new Object[tableNodes.size()];
        int index = 0;
        for (TableNode tableNode : tableNodes) {
            Object instance = this.mappingObj(dialect, tableNode, resultSet);
            if (index == 0 && null == instance) {
                return null;
            }
            instances[index ++] = instance;
        }
        return instances;
    }

    protected Object mappingObj(Dialect dialect, TableNode tableNode, ResultSet resultSet) throws SQLException {
        FieldDescriptor idDescriptor = tableNode.getIdDescriptor();
        Object id;
        if (null == (id = JdbcUtils.getResultSetValue(resultSet, idDescriptor.getColumnIndex() + 1, idDescriptor.getJavaType()))) {
            return null;
        }
        Object instance = ReflectUtil.newInstance(tableNode.getPojoType());
        for (FieldDescriptor fieldDescriptor : tableNode.getFieldMapping().values()) {
            if (fieldDescriptor == idDescriptor) {
                ReflectUtil.invokeSetter(idDescriptor.getSetter(), instance, id);
                continue;
            }
            if (fieldDescriptor.isIgnored()) {
                continue;
            }
            int columnIndex = fieldDescriptor.getColumnIndex() + 1;
            Object value;
            if (fieldDescriptor.havingTypeConverter()) {
                TypeConverter converter = this.specialColumnProvider.provider(fieldDescriptor.getColumn().getTypeConverter());
                // 满足当前方言，再执行转换器
                if (converter.matchDialect(dialect)) {
                    try {
                        value = converter.convertFromDb(resultSet, columnIndex, fieldDescriptor.getJavaType());
                    } catch (SQLException sqlException) {
                        throw new TypeConvertException("类型转换异常：" + instance.getClass().getName() + "#" + fieldDescriptor.getSetter().getName() + "", sqlException);
                    }
                } else {
                    value = JdbcUtils.getResultSetValue(resultSet, columnIndex, fieldDescriptor.getJavaType());
                }
            } else {
                value = JdbcUtils.getResultSetValue(resultSet, columnIndex, fieldDescriptor.getJavaType());
            }
            ReflectUtil.invokeSetter(fieldDescriptor.getSetter(), instance, value);
        }
        return instance;
    }
}
