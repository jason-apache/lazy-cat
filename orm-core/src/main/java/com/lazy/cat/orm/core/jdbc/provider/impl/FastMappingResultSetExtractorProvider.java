package com.lazy.cat.orm.core.jdbc.provider.impl;

import com.lazy.cat.orm.core.base.util.Caster;
import com.lazy.cat.orm.core.jdbc.JdbcConstant;
import com.lazy.cat.orm.core.jdbc.analyzer.RowAggregator;
import com.lazy.cat.orm.core.jdbc.component.convert.TypeConverter;
import com.lazy.cat.orm.core.jdbc.dialect.Dialect;
import com.lazy.cat.orm.core.jdbc.dialect.DialectRegister;
import com.lazy.cat.orm.core.jdbc.dto.FlatPojoWrapper;
import com.lazy.cat.orm.core.jdbc.dto.TableFieldInfoIndexWrapper;
import com.lazy.cat.orm.core.jdbc.exception.RowMappingException;
import com.lazy.cat.orm.core.jdbc.exception.TypeConvertException;
import com.lazy.cat.orm.core.jdbc.holder.SearchSqlParamIndexHolder;
import com.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import com.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import com.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import com.lazy.cat.orm.core.jdbc.provider.ResultSetExtractorProvider;
import com.lazy.cat.orm.core.jdbc.provider.TypeConverterProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/28 19:08
 */
public class FastMappingResultSetExtractorProvider implements ResultSetExtractorProvider {

    @Autowired
    protected RowAggregator rowAggregator;
    @Autowired
    protected PojoTableManager pojoTableManager;
    @Autowired
    protected TypeConverterProvider typeConverterProvider;
    protected Dialect dialect;
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private void initDialect(DialectRegister register) {
        this.dialect = register.getDialect();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ResultSetExtractor<List<T>> provider(TableChainHolder tableChainHolder, SqlParamHolder sqlParamHolder, int initialCapacity) {
        SearchSqlParamIndexHolder searchSqlParamIndexHolder = (SearchSqlParamIndexHolder) sqlParamHolder;
        return resultSet -> {
            long rowMappingTime = 0;
            long start;
            int rowNum = 0;
            List<Object> result = new ArrayList<>(JdbcConstant.DEFAULT_CONTAINER_SIZE);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int maxColumn = metaData.getColumnCount();
            TableInfo tableInfo = tableChainHolder.getTableInfo();
            try {
                if (tableInfo.isNested()) {
                    while (resultSet.next()) {
                        start = System.currentTimeMillis();
                        FlatPojoWrapper[] instanceDef = (FlatPojoWrapper[]) this.initInstance(tableChainHolder);
                        this.mappingRow(resultSet, maxColumn, instanceDef, tableChainHolder.getFlatChain(), searchSqlParamIndexHolder.getIndexesArr());
                        result.add(instanceDef);
                        rowNum ++;
                        rowMappingTime += System.currentTimeMillis() - start;
                    }
                } else {
                    while (resultSet.next()) {
                        start = System.currentTimeMillis();
                        Object instanceDef = this.initInstance(tableChainHolder);
                        this.mappingRow(resultSet, maxColumn, instanceDef, searchSqlParamIndexHolder.getIndexesArr());
                        result.add(instanceDef);
                        rowNum ++;
                        rowMappingTime += System.currentTimeMillis() - start;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RowMappingException("行映射异常：" + tableInfo.getPojoType().getName() + "，rowNumber："+ rowNum);
            }
            logger.info("映射总耗时" + rowMappingTime + "\t对象：" + rowNum);
            if (tableInfo.isNested()) {
                // 合并一对多映射
                return (List<T>) rowAggregator.mergeRow(tableChainHolder, searchSqlParamIndexHolder.getExcludeFieldInfoWrapper(), Caster.cast(result));
            }
            return (List<T>) result;
        };
    }

    /**
     * 处理当前行数据（嵌套的对象集）
     * @param resultSet 结果集
     * @param maxColumn 最大列
     * @param instanceDef 实例集引用
     * @param flatChain 平铺的表链
     * @param indexes 列映射关系
     * @throws IllegalAccessException 反射引发的异常
     * @throws SQLException 数据库交互引发的异常
     * @throws InvocationTargetException 反射引发的异常
     */
    private void mappingRow(ResultSet resultSet, int maxColumn, FlatPojoWrapper[] instanceDef, List<TableChain> flatChain,
                            TableFieldInfoIndexWrapper[] indexes) throws IllegalAccessException, SQLException, InvocationTargetException {
        for (int i = 1; i <= maxColumn; i++) {
            TableFieldInfoIndexWrapper indexWrapper = indexes[i -1];
            if (null == indexWrapper || null == indexWrapper.getFieldInfo()) {
                continue;
            }
            TableFieldInfo info = indexWrapper.getFieldInfo();
            Object pojo;
            if (indexWrapper.getChainFlatIndex() != -1) {
                TableChain chain = flatChain.get(indexWrapper.getChainFlatIndex() - 1);
                pojo = instanceDef[chain.getFlatIndex()].getPojoInstance();
            } else {
                pojo = instanceDef[0].getPojoInstance();
            }
            this.invoke(info, pojo, resultSet, i);
        }
    }

    /**
     * 处理当前行数据（嵌套的对象集）
     * @param resultSet 结果集
     * @param maxColumn 最大列
     * @param instance 实例引用
     * @param indexes 列映射关系
     * @throws IllegalAccessException 反射引发的异常
     * @throws SQLException 数据库交互引发的异常
     * @throws InvocationTargetException 反射引发的异常
     */
    private void mappingRow(ResultSet resultSet, int maxColumn, Object instance, TableFieldInfoIndexWrapper[] indexes)
            throws IllegalAccessException, SQLException, InvocationTargetException {
        for (int i = 1; i <= maxColumn; i++) {
            TableFieldInfoIndexWrapper indexWrapper = indexes[i -1];
            if (null == indexWrapper || null == indexWrapper.getFieldInfo()) {
                continue;
            }
            TableFieldInfo info = indexWrapper.getFieldInfo();
            this.invoke(info, instance, resultSet, i);
        }
    }

    /**
     * 初始化对象
     * @param tableChainHolder 表映射
     * @return 对象集合或者单个对象
     * @throws IllegalAccessException 反射引发的异常
     * @throws InstantiationException 反射引发的异常
     */
    private Object initInstance(TableChainHolder tableChainHolder) throws IllegalAccessException, InstantiationException {
        if (tableChainHolder.getTableInfo().isNested()) {
            int flatPojoCount = tableChainHolder.getFlatChain().size();
            FlatPojoWrapper[] pojoWrapper = new FlatPojoWrapper[flatPojoCount + 1];
            pojoWrapper[0] = new FlatPojoWrapper(0, tableChainHolder.getTableInfo().getPojoType().newInstance());
            for (int i = 0; i < flatPojoCount; i++) {
                pojoWrapper[i + 1] = new FlatPojoWrapper(0, tableChainHolder.getFlatChain().get(i).getPojoType().newInstance());
            }
            return pojoWrapper;
        } else {
            return tableChainHolder.getTableInfo().getPojoType().newInstance();
        }
    }

    /**
     * 完成数据的赋值操作
     * @param info 字段信息
     * @param instance pojo实例
     * @param resultSet 数据集
     * @param column 列号
     * @throws SQLException 数据库交互引发的异常
     * @throws InvocationTargetException 反射引发的异常
     * @throws IllegalAccessException 反射引发的异常
     */
    private void invoke(TableFieldInfo info, Object instance, ResultSet resultSet, int column) throws SQLException, InvocationTargetException, IllegalAccessException {
        if (info.havingTypeConverter()) {
            TypeConverter converter = this.typeConverterProvider.provider(info.getColumn().getTypeConverter());
            // 满足当前方言，再执行转换器
            if (converter.match(dialect)) {
                info.getSetter().invoke(instance, JdbcUtils.getResultSetValue(resultSet, column, info.getJavaType()));
            } else {
                try {
                    info.getSetter().invoke(instance, converter.convertFromDb(instance, resultSet, column, info.getJavaType()));
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    throw new TypeConvertException("类型转换异常：" + instance.getClass().getName() + "#" + info.getSetter().getName() + "");
                }
            }
        } else {
            info.getSetter().invoke(instance, JdbcUtils.getResultSetValue(resultSet, column, info.getJavaType()));
        }
    }
}
