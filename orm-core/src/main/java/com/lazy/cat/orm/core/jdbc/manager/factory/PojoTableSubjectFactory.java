package com.lazy.cat.orm.core.jdbc.manager.factory;

import com.lazy.cat.orm.core.base.annotation.Column;
import com.lazy.cat.orm.core.base.annotation.Id;
import com.lazy.cat.orm.core.base.annotation.LogicDelete;
import com.lazy.cat.orm.core.base.annotation.ManyToOne;
import com.lazy.cat.orm.core.base.annotation.OneToMany;
import com.lazy.cat.orm.core.base.annotation.OneToOne;
import com.lazy.cat.orm.core.base.annotation.Pojo;
import com.lazy.cat.orm.core.base.annotation.Table;
import com.lazy.cat.orm.core.base.constant.Constant;
import com.lazy.cat.orm.core.base.exception.FieldAlreadyExistsException;
import com.lazy.cat.orm.core.base.util.CollectionUtil;
import com.lazy.cat.orm.core.base.util.StringUtil;
import com.lazy.cat.orm.core.jdbc.JdbcConfig;
import com.lazy.cat.orm.core.jdbc.KeyWordConverter;
import com.lazy.cat.orm.core.jdbc.component.id.IdGenerator;
import com.lazy.cat.orm.core.jdbc.dto.CascadeLevelMapper;
import com.lazy.cat.orm.core.jdbc.exception.CannotFindJoinConditionException;
import com.lazy.cat.orm.core.jdbc.exception.UniqueKeyUndefinedException;
import com.lazy.cat.orm.core.jdbc.generator.AliasNameGenerator;
import com.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import com.lazy.cat.orm.core.jdbc.manager.subject.PojoTableSubject;
import com.lazy.cat.orm.core.jdbc.mapping.IdStrategy;
import com.lazy.cat.orm.core.jdbc.mapping.LogicDeleteField;
import com.lazy.cat.orm.core.jdbc.mapping.On;
import com.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import com.lazy.cat.orm.core.jdbc.util.PlaceHolderUtil;
import com.lazy.cat.orm.core.jdbc.util.TableChainBuildHelper;
import com.lazy.cat.orm.core.manager.PojoManager;
import com.lazy.cat.orm.core.manager.exception.CannotFindSetterMethodException;
import com.lazy.cat.orm.core.manager.exception.MutualExclusionException;
import com.lazy.cat.orm.core.manager.exception.NotStandardSetterMethodException;
import com.lazy.cat.orm.core.manager.factory.SubjectFactory;
import com.lazy.cat.orm.core.manager.subject.PojoSubject;
import com.lazy.cat.orm.core.manager.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: mahao
 * @date: 2021/3/11 12:30
 */
public class PojoTableSubjectFactory implements SubjectFactory {

    @Autowired
    protected KeyWordConverter keyWordConverter;
    @Autowired
    protected JdbcConfig jdbcConfig;
    @Autowired
    protected AliasNameGenerator aliasNameGenerator;
    @Autowired
    protected PojoManager pojoManager;

    @PostConstruct
    public void init() {
        // 初始化工具类
        TableChainBuildHelper.initAliasNameGenerator(aliasNameGenerator);
    }

    @Override
    public Subject build(Class<?> pojoType) {
        PojoTableSubject subject = new PojoTableSubject();
        subject.setPojoType(pojoType);
        this.initPojoTableSubjectTableInfo(subject);
        return subject;
    }

    protected void initPojoTableSubjectTableInfo(PojoTableSubject pojoTableSubject) {
        Class<?> pojoType = pojoTableSubject.getPojoType();
        Table annotation = pojoType.getAnnotation(Pojo.class).table();
        TableInfo tableInfo = new TableInfo();
        if (StringUtil.isNotBlank(annotation.tableName())) {
            tableInfo.setName(annotation.tableName());
        } else {
            tableInfo.setName(keyWordConverter.toDbWord(pojoType.getSimpleName()));
        }
        if (StringUtil.isNotBlank(annotation.schema())) {
            tableInfo.setSchema(PlaceHolderUtil.getDynamicSchema(annotation.schema(), jdbcConfig));
        }
        tableInfo.setPojoType(pojoTableSubject.getPojoType());
        pojoTableSubject.setTableInfo(tableInfo);
        this.initPojoTableSubjectFiledInfo(pojoTableSubject, tableInfo);
    }

    /**
     * 初始化数据库表映射
     * @param pojoTableSubject pojo表映射对象
     * @param tableInfo 数据库表映射
     */
    protected void initPojoTableSubjectFiledInfo(PojoTableSubject pojoTableSubject, TableInfo tableInfo) {
        PojoSubject pojoSubject = pojoManager.getByPojoType(pojoTableSubject.getPojoType());
        tableInfo.addTrigger(pojoSubject.getPojoType().getAnnotation(Pojo.class).trigger());
        List<MethodDescriptor> getterList = pojoSubject.getGetterList();
        this.filterGetter(getterList, pojoSubject.getPojoType());
        List<MethodDescriptor> setterList = pojoSubject.getSetterList();
        int index = 0;
        for (MethodDescriptor getterDes : getterList) {
            TableFieldInfo fieldInfo = new TableFieldInfo();
            fieldInfo.setPojoType(pojoSubject.getPojoType());
            Method getter = getterDes.getMethod();
            this.checkGetter(pojoTableSubject.getPojoType(), getter);
            Method setter = findSetterMethodByGetter(pojoTableSubject.getPojoType(), getter, setterList);
            getter.setAccessible(true);
            fieldInfo.setGetter(getter);
            fieldInfo.setJavaFieldName(StringUtil.upper2Lower(getter.getName().substring(Constant.GET_METHOD_PREFIX.length())));
            fieldInfo.setAliasName(aliasNameGenerator.generatorFiledName(fieldInfo.getJavaFieldName(), index++));
            setter.setAccessible(true);
            fieldInfo.setSetter(setter);
            fieldInfo.setJavaType(getter.getReturnType());
            Column column = getter.getAnnotation(Column.class);
            if (null != column) {
                if (StringUtil.isBlank(column.name())) {
                    fieldInfo.setDbFieldName(keyWordConverter.toDbWord(getter.getName().substring(Constant.GET_METHOD_PREFIX.length())));
                } else {
                    fieldInfo.setDbFieldName(column.name());
                }
                tableInfo.addFiledInfo(fieldInfo);
            }
            fieldInfo.initColumn(column);
            tableInfo.addAnnotation(getter.getAnnotation(OneToMany.class), fieldInfo.getJavaType(), fieldInfo);
            tableInfo.addAnnotation(getter.getAnnotation(OneToOne.class), fieldInfo.getJavaType(), fieldInfo);
            tableInfo.addAnnotation(getter.getAnnotation(ManyToOne.class), fieldInfo.getJavaType(), fieldInfo);
            Id idAnnotation = getter.getAnnotation(Id.class);
            if (null != idAnnotation) {
                this.setId(idAnnotation, fieldInfo, tableInfo);
            }
            LogicDelete logicDelete = getter.getAnnotation(LogicDelete.class);
            if (null != logicDelete) {
                this.setLogicDeleteField(logicDelete, fieldInfo, tableInfo);
            }
        }
    }

    /**
     * 过滤非法参数、方法、注解
     * @param getterList pojo中的所有getter方法
     * @param pojoType pojo类型
     */
    private void filterGetter(List<MethodDescriptor> getterList, Class<?> pojoType) {
        MethodDescriptor id = getterList.stream().filter(g -> g.getMethod().getAnnotation(Id.class) != null).findFirst().orElse(null);
        if (null == id) {
            throw new UniqueKeyUndefinedException("pojo未定义主键：" + pojoType.getName());
        }
        if (null == id.getMethod().getAnnotation(Column.class)) {
            throw new IllegalArgumentException("主键字段必须定义@Column注解：" + pojoType.getName() + "#" + id.getMethod().getName());
        }
        if (!id.getMethod().getAnnotation(Column.class).insertable()) {
            throw new IllegalArgumentException("主键列insertable必须为true：" + pojoType.getName() + "#" + id.getMethod().getName());
        }
        MethodDescriptor logicDeleteFiled = getterList.stream().filter(g -> g.getMethod().getAnnotation(LogicDelete.class) != null).findFirst().orElse(null);
        if (null != logicDeleteFiled) {
            long count = getterList.stream().filter(g -> g.getMethod().getAnnotation(LogicDelete.class) != null).count();
            if (count > 1) {
                throw new FieldAlreadyExistsException("重复的逻辑删除字段定义：" + pojoType.getName() + "#" + logicDeleteFiled.getMethod().getName());
            }
            if (null == logicDeleteFiled.getMethod().getAnnotation(Column.class)) {
                throw new IllegalArgumentException("逻辑删除字段必须定义@Column注解：" + pojoType.getName() + "#" + logicDeleteFiled.getMethod().getName());
            }
        }
        getterList.removeIf(g -> (g.getMethod().getAnnotation(Column.class) == null && g.getMethod().getAnnotation(OneToMany.class) == null
                && g.getMethod().getAnnotation(OneToOne.class) == null && g.getMethod().getAnnotation(ManyToOne.class) == null)
                || g.getMethod().getParameterTypes().length > 1);
    }

    /**
     * 检查getter方法合法性
     * @param pojoType pojo类型
     * @param getter get方法
     */
    private void checkGetter(Class<?> pojoType, Method getter) {
        Column column = getter.getAnnotation(Column.class);
        OneToMany oneToMany = getter.getAnnotation(OneToMany.class);
        OneToOne oneToOne = getter.getAnnotation(OneToOne.class);
        ManyToOne manyToOne = getter.getAnnotation(ManyToOne.class);
        long count = Stream.of(column, oneToMany, oneToOne, manyToOne).filter(Objects::nonNull).count();
        if (count > 1) {
            throw new MutualExclusionException("Column OneToMany OneToOne ManyToOne不应同时出现，请检查：" + pojoType.getName() + "#" + getter.getName());
        }
    }

    /**
     * 根据getter方法签名查找与之对应的setter
     * @param pojoType pojo类型
     * @param getter getter方法
     * @param setterList pojo中的所有setter
     * @return 返回与getter方法签名的对应的setter
     */
    protected Method findSetterMethodByGetter(Class<?> pojoType, Method getter, List<MethodDescriptor> setterList) {
        String setterName = Constant.SET_METHOD_PREFIX + getter.getName().substring(Constant.GET_METHOD_PREFIX.length());
        for (MethodDescriptor setter : setterList) {
            if (setterName.equals(setter.getName())) {
                Class<?> setterType = setter.getMethod().getParameterTypes()[0];
                boolean notMatch = setterType != getter.getReturnType() && !setterType.isAssignableFrom(getter.getReturnType());
                if (setter.getMethod().getParameterCount() > 1 || notMatch) {
                    throw new NotStandardSetterMethodException("set方法不符合规范、或setter getter参数不一致："+ pojoType.getName() + "#" + setter.getName());
                }
                return setter.getMethod();
            }
        }
        throw new CannotFindSetterMethodException("无法匹配对应的set方法：" + pojoType.getName() + "#" + getter.getName()
                + "\t请检查方法命名是否符合规范");
    }

    /**
     * 初始化主键信息
     * @param idAnnotation 主键注解
     * @param fieldInfo 字段信息
     * @param tableInfo 表信息
     */
    protected void setId(Id idAnnotation, TableFieldInfo fieldInfo, TableInfo tableInfo) {
        if (idAnnotation.idGenerator() == IdGenerator.class) {
            throw new IllegalArgumentException("不是一个实现类：" + idAnnotation.idGenerator().getName());
        }
        IdStrategy id = new IdStrategy();
        BeanUtils.copyProperties(fieldInfo, id);
        id.setIdGenerator(idAnnotation.idGenerator()).setSequenceInfo(idAnnotation.sequence());
        if (null != id.getSequenceInfo() && StringUtil.isNotBlank(id.getSequenceInfo().getSchema())) {
            id.getSequenceInfo().setSchema(PlaceHolderUtil.getDynamicSchema(id.getSequenceInfo().getSchema(), jdbcConfig));
        }
        tableInfo.setId(id);
    }

    public void setLogicDeleteField(LogicDelete logicDelete, TableFieldInfo fieldInfo, TableInfo tableInfo) {
        LogicDeleteField logicDeleteField = new LogicDeleteField();
        BeanUtils.copyProperties(fieldInfo, logicDeleteField);
        logicDeleteField.setDeleteValue(logicDelete.deleteValue()).setNormalValue(logicDelete.normalValue());
        tableInfo.setLogicDeleteField(logicDeleteField);
    }

    /**
     * 初始化关联查询条件
     * @param pojoTableSubjectMap 所有pojo表映射
     */
    public void initJoinCondition(Map<Class<?>, PojoTableSubject> pojoTableSubjectMap) {
        for (PojoTableSubject repositorySubject : pojoTableSubjectMap.values()) {
            if (CollectionUtil.isNotEmpty(repositorySubject.getTableInfo().getOneToOneMapping())) {
                repositorySubject.getTableInfo().getOneToOneMapping().forEach(a -> this.initJoinCondition(a, pojoTableSubjectMap));
            }
            if (CollectionUtil.isNotEmpty(repositorySubject.getTableInfo().getOneToManyMapping())) {
                repositorySubject.getTableInfo().getOneToManyMapping().forEach(a -> this.initJoinCondition(a, pojoTableSubjectMap));
            }
            if (CollectionUtil.isNotEmpty(repositorySubject.getTableInfo().getManyToOneMapping())) {
                repositorySubject.getTableInfo().getManyToOneMapping().forEach(a -> this.initJoinCondition(a, pojoTableSubjectMap));
            }
        }
    }

    /**
     * 初始化关联查询条件 检查合法性
     * @param pojoMapping 关联条件
     * @param pojoTableSubjectMap 所有pojo表映射
     */
    private void initJoinCondition(PojoMapping pojoMapping, Map<Class<?>, PojoTableSubject> pojoTableSubjectMap) {
        for (On condition : pojoMapping.getJoinCondition()) {
            String foreignKey = condition.getForeignField();
            String targetFiled = condition.getTargetFiled();
            Class<?> foreignPojoType = condition.getForeignPojoType();
            Class<?> targetPojoType = condition.getTargetPojoType();
            if (!pojoTableSubjectMap.containsKey(targetPojoType)) {
                throw new CannotFindJoinConditionException("无法匹配联查条件，缺失的依赖项："+ foreignPojoType.getName() + "#" + pojoMapping.getFieldInfo().getGetter().getName() + " -> #" + targetPojoType.getName() + "\t该类可能无法被扫描或缺失@Pojo");
            }
            TableFieldInfo foreignKeyFieldInfo = pojoTableSubjectMap.get(foreignPojoType).getTableInfo().getFieldInfoList().stream()
                    .filter(f -> f.getJavaFieldName().equals(foreignKey)).findFirst().orElseThrow(
                            () -> new CannotFindJoinConditionException("无法匹配联查条件，请检查：" + foreignPojoType.getName() + "#" + foreignKey + "\t" + targetPojoType.getName() + "#" + targetFiled));
            TableFieldInfo targetFieldInfo = pojoTableSubjectMap.get(targetPojoType).getTableInfo().getFieldInfoList().stream()
                    .filter(f -> f.getJavaFieldName().equals(targetFiled)).findFirst().orElseThrow(
                            () -> new CannotFindJoinConditionException("无法匹配联查条件，请检查：" + foreignPojoType.getName() + "#" + foreignKey + "\t" + targetPojoType.getName() + "#" + targetFiled));
            condition.setForeignKeyInfo(foreignKeyFieldInfo);
            condition.setTargetFiledInfo(targetFieldInfo);
        }
    }

    /**
     * 解析生成tableChain对象
     * @param allSubjectMap 所有pojo表映射
     */
    public void analysisTableChain(Map<Class<?>, PojoTableSubject> allSubjectMap) {
        List<PojoTableSubject> hasMappingSubject = allSubjectMap.values().stream().filter(this::hasMapping).collect(Collectors.toList());
        for (PojoTableSubject subject : hasMappingSubject) {
            TableInfo tableInfo = subject.getTableInfo();
            List<PojoMapping> mappings = CollectionUtil.concat(tableInfo.getOneToOneMapping(), tableInfo.getOneToManyMapping(), tableInfo.getManyToOneMapping());
            TableChainHolder holder = TableChainBuildHelper.build(tableInfo, mappings, allSubjectMap, CascadeLevelMapper.buildEmpty());
            tableInfo.setNestedChain(holder.getNestedChain());
            tableInfo.setFlatChain(holder.getFlatChain());
        }
    }

    private boolean hasMapping(PojoTableSubject subject) {
        return subject.getTableInfo().getOneToOneMapping() != null || subject.getTableInfo().getOneToManyMapping() != null
                || subject.getTableInfo().getManyToOneMapping() != null;
    }
}
