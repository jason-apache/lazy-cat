package com.lazy.cat.orm.core.base.service.impl;

import com.lazy.cat.orm.core.base.AbstractFullAutomaticMapping;
import com.lazy.cat.orm.core.base.bo.QueryInfo;
import com.lazy.cat.orm.core.base.repository.BaseRepository;
import com.lazy.cat.orm.core.base.service.BaseService;
import com.lazy.cat.orm.core.base.util.Caster;
import com.lazy.cat.orm.core.base.util.InvokeHelper;
import com.lazy.cat.orm.core.context.FullAutoMappingContext;
import com.lazy.cat.orm.core.jdbc.DataOperationType;
import com.lazy.cat.orm.core.jdbc.condition.Condition;
import com.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import com.lazy.cat.orm.core.jdbc.mapping.LogicDeleteField;
import com.lazy.cat.orm.core.jdbc.mapping.On;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import com.lazy.cat.orm.core.manager.BusinessManager;
import com.lazy.cat.orm.core.manager.subject.BusinessSubject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/5 09:57
 */
public abstract class AbstractService<P> extends AbstractFullAutomaticMapping<P> implements BaseService<P> {

    @Autowired
    @Lazy
    protected BusinessManager businessManager;
    @Autowired
    protected PojoTableManager pojoTableManager;
    protected final Log logger = LogFactory.getLog(getClass());

    protected Class<?> tryGetPojoType() {
        Class<?> pojoType = FullAutoMappingContext.getPojoType();
        return pojoType == null ? super.getPojoType() : pojoType;
    }

    private BusinessSubject getSubject(Class<?> pojoType) {
        return businessManager.getBusinessSubject(pojoType);
    }

    protected BaseService<?> getService(Class<?> pojoType) {
        return this.getSubject(pojoType).getServiceSubject().getService();
    }

    protected BaseRepository<?> getRepository(Class<?> pojoType) {
        return this.getSubject(pojoType).getRepositorySubject().getRepository();
    }

    /**
     * 从pojo实例中获取id属性的名称
     * @param instance pojo实例
     * @return id属性名
     */
    protected String getPojoIdName(Object instance) {
        return this.getPojoIdName(instance.getClass());
    }

    /**
     * 从pojo类型中获取id属性的名称
     * @param pojoType pojo类型
     * @return id属性名
     */
    protected String getPojoIdName(Class<?> pojoType) {
        return pojoTableManager.getByPojoType(pojoType).getTableInfo().getId().getJavaFieldName();
    }

    /**
     * 从pojo实例中获取id的值
     * @param instance pojo实例
     * @return id值
     */
    protected Object getPojoId(Object instance) {
        return InvokeHelper.invokeGetter(pojoTableManager.getByPojoType(instance.getClass()).getTableInfo().getId().getGetter(), instance);
    }

    /**
     * 初始化默认查询条件
     * 如果pojo存在逻辑删除字段，则添加逻辑删除字段控制
     * @param queryInfo API查询参数
     */
    protected void initCondition(QueryInfo queryInfo) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(tryGetPojoType()).getTableInfo();
        LogicDeleteField logicDeleteField = tableInfo.getLogicDeleteField();
        if (null != logicDeleteField) {
            if (queryInfo.getParams() == null) {
                queryInfo.setParams(Collections.singletonMap(logicDeleteField.getJavaFieldName(), logicDeleteField.getNormalValue()));
            } else {
                Map<String, Object> params = queryInfo.getParams();
                if (!params.containsKey(logicDeleteField.getJavaFieldName())) {
                    params.put(logicDeleteField.getJavaFieldName(), logicDeleteField.getNormalValue());
                }
            }
        }
    }

    /**
     * 初始化默认查询条件
     * 如果pojo存在逻辑删除字段，则添加逻辑删除字段控制
     * @param pojoType pojo类型
     * @return 默认条件
     */
    protected Condition initCondition(Class<?> pojoType) {
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();
        if (null != tableInfo.getLogicDeleteField()) {
            LogicDeleteField logicDeleteField = tableInfo.getLogicDeleteField();
            return Condition.eq(logicDeleteField.getJavaFieldName(), logicDeleteField.getNormalValue());
        }
        return null;
    }

    /**
     * 进行级联操作
     * @param type 数据操作类型
     * @param src 操作源对象
     * @param cascade 级联操作
     */
    protected void dataOperation(DataOperationType type, Object src, boolean cascade) {
        if (cascade) {
            TableInfo tableInfo = pojoTableManager.getByPojoType(src.getClass()).getTableInfo();
            if (tableInfo.isNested()) {
                this.dataOperation(type, tableInfo, tableInfo.getNestedChain(), src, cascade);
            }
        }
    }

    /**
     * 进行级联操作
     * @param type 数据操作类型
     * @param tableInfo pojo表映射
     * @param nestedChainList 嵌套的表链调用结构
     * @param src 操作源对象
     * @param cascade 级联操作
     */
    protected void dataOperation(DataOperationType type, TableInfo tableInfo, List<TableChain> nestedChainList, Object src, boolean cascade) {
        if (cascade) {
            if (tableInfo.isNested()) {
                this.recursionUpdate(type, src, nestedChainList);
            }
        }
    }

    /**
     * 级联，递归操作对象
     * @param type 数据操作类型
     * @param src 操作源对象
     * @param nestedChainList 嵌套的表链调用结构
     */
    private void recursionUpdate(DataOperationType type, Object src, List<TableChain> nestedChainList) {
        for (TableChain chain : nestedChainList) {
            Object newSrc = InvokeHelper.invokeGetter(chain.getBelongField().getGetter(), src);
            if (null != newSrc) {
                if (newSrc instanceof Collection) {
                    for (Object o : ((Collection<?>) newSrc)) {
                        this.updateSingleObj(src, chain, o, type);
                    }
                } else {
                    this.updateSingleObj(src, chain, newSrc, type);
                }
                if (chain.hasChain()) {
                    if (newSrc instanceof Collection) {
                        for (Object o : ((Collection<?>) newSrc)) {
                            this.recursionUpdate(type, o, chain.getChain());
                        }
                    } else {
                        this.recursionUpdate(type, newSrc, chain.getChain());
                    }
                }
            }
        }
    }

    /**
     * 执行数据更新
     * @param src 操作元数据
     * @param chain 当前操作对象的表链结构
     * @param instance 当前操作对象
     * @param type 数据操作类型
     */
    private void updateSingleObj(Object src, TableChain chain, Object instance, DataOperationType type) {
        if (type == DataOperationType.DELETE) {
            if (chain.getPojoMapping().isDeletable()) {
                this.getService(instance.getClass()).delete(Caster.cast(instance), false);
            }
        } else if (type == DataOperationType.LOGIC_DELETE) {
            if (chain.getPojoMapping().isDeletable()) {
                this.getService(instance.getClass()).logicDelete(Caster.cast(instance), false);
            }
        } else {
            this.beforeCascadeUpdate(src, instance, chain);
            if (null == this.getPojoId(instance)) {
                if (chain.getPojoMapping().isInsertable()) {
                    this.getService(instance.getClass()).insert(Caster.cast(instance), false);
                }
            } else {
                if (chain.getPojoMapping().isUpdatable()) {
                    this.getService(instance.getClass()).update(Caster.cast(instance), false);
                }
            }
        }
    }

    /**
     * 进行数据关联字段的赋值
     * @param src 操作源对象
     * @param instance 当前操作对象
     * @param chain 当前操作对象的表链结构
     */
    private void beforeCascadeUpdate(Object src, Object instance, TableChain chain) {
        List<On> chainJoinCondition = chain.getJoinCondition();
        for (On on : chainJoinCondition) {
            Object foreignVal = InvokeHelper.invokeGetter(on.getForeignKeyInfo().getGetter(), src);
            InvokeHelper.invokeSetter(on.getTargetFiledInfo().getSetter(), instance, foreignVal);
        }
    }
}
