package cool.lazy.cat.orm.core.manager.factory;

import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.LogicDelete;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.OneToMany;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.annotation.Trigger;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.util.StringUtil;
import cool.lazy.cat.orm.core.base.exception.FieldAlreadyExistsException;
import cool.lazy.cat.orm.core.base.exception.UnsupportedTypeException;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.exception.CannotFindJoinConditionException;
import cool.lazy.cat.orm.core.jdbc.exception.UniqueKeyUndefinedException;
import cool.lazy.cat.orm.core.jdbc.mapping.ManyToOneMappingImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.On;
import cool.lazy.cat.orm.core.jdbc.mapping.OneToManyMappingImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.OneToOneMappingImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfoImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.TriggerInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TriggerInfoImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.field.PojoFieldMapper;
import cool.lazy.cat.orm.core.jdbc.mapping.field.PojoFieldMapperImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.CascadeSelfAdaptionFieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdFieldImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.LogicDeleteFieldImpl;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.TableFieldInfo;
import cool.lazy.cat.orm.core.manager.PojoManager;
import cool.lazy.cat.orm.core.manager.exception.CannotFindSetterMethodException;
import cool.lazy.cat.orm.core.manager.exception.MutualExclusionException;
import cool.lazy.cat.orm.core.manager.exception.NotStandardSetterMethodException;
import cool.lazy.cat.orm.core.manager.subject.PojoSubject;
import cool.lazy.cat.orm.core.manager.subject.PojoTableSubject;
import cool.lazy.cat.orm.core.manager.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;

import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: mahao
 * @date: 2021/3/11 12:30
 */
public class PojoTableSubjectFactoryImpl implements PojoTableSubjectFactory {

    protected final PojoManager pojoManager;

    public PojoTableSubjectFactoryImpl(PojoManager pojoManager) {
        this.pojoManager = pojoManager;
    }

    @Override
    public Subject build(Class<?> pojoType) {
        PojoTableSubject subject = new PojoTableSubject();
        subject.setPojoType(pojoType);
        this.initPojoTableSubjectTableInfo(pojoManager.getByPojoType(pojoType), subject);
        return subject;
    }

    @Override
    public Collection<PojoTableSubject> create() {
        List<PojoSubject> pojoSubjectList = pojoManager.getPojoSubjectList();
        Map<Class<?>, PojoTableSubject> pojoTableSubjectMap = new HashMap<>(pojoSubjectList.size());
        for (PojoSubject pojoSubject : pojoSubjectList) {
            PojoTableSubject pojoTableSubject = new PojoTableSubject();
            pojoTableSubject.setPojoType(pojoSubject.getPojoType());
            this.initPojoTableSubjectTableInfo(pojoSubject, pojoTableSubject);
            pojoTableSubjectMap.put(pojoTableSubject.getPojoType(), pojoTableSubject);
        }
        this.initJoinCondition(pojoTableSubjectMap);
        this.analysisTableChain(pojoTableSubjectMap);
        return pojoTableSubjectMap.values();
    }

    protected void initPojoTableSubjectTableInfo(PojoSubject pojoSubject, PojoTableSubject pojoTableSubject) {
        Class<?> pojoType = pojoTableSubject.getPojoType();
        Table annotation = pojoType.getAnnotation(Pojo.class).table();
        TableInfoImpl tableInfoImpl = new TableInfoImpl(pojoTableSubject.getPojoType(), annotation);
        if (StringUtil.isNotBlank(annotation.tableName())) {
            tableInfoImpl.setName(annotation.tableName());
        } else {
            tableInfoImpl.setName(StringUtil.camel2Underline(pojoType.getSimpleName(), JdbcConstant.DEFAULT_CASE));
        }
        tableInfoImpl.setSchema(annotation.schema());
        pojoTableSubject.setTableInfo(tableInfoImpl);
        this.initPojoTableSubjectFiledInfo(pojoSubject, pojoTableSubject, tableInfoImpl);
    }

    /**
     * ???????????????????????????
     * @param pojoTableSubject pojo???????????????
     * @param tableInfoImpl ??????????????????
     */
    protected void initPojoTableSubjectFiledInfo(PojoSubject pojoSubject, PojoTableSubject pojoTableSubject, TableInfoImpl tableInfoImpl) {
        Set<MethodDescriptor> getters = new HashSet<>(pojoSubject.getGetters());
        // ??????
        this.filterGetter(getters, pojoSubject.getPojoType());
        Set<MethodDescriptor> setters = new HashSet<>(pojoSubject.getSetters());
        // ????????????
        List<PojoField> pojoFields = new ArrayList<>();
        // ????????????????????????
        List<PojoMapping> mappings = new ArrayList<>();
        for (MethodDescriptor getterDes : getters) {
            Method getter = getterDes.getMethod();
            getter.setAccessible(true);
            this.checkGetter(pojoTableSubject.getPojoType(), getter);
            Method setter = this.findSetterMethodByGetter(pojoTableSubject.getPojoType(), getter, setters);
            setter.setAccessible(true);
            TableFieldInfo fieldInfo = new TableFieldInfo(getter, setter);
            fieldInfo.setJavaFieldName(StringUtil.upper2Lower(getter.getName().substring(Constant.GET_METHOD_PREFIX.length())));
            Id idAnnotation = getter.getAnnotation(Id.class);
            if (null != idAnnotation) {
                fieldInfo = this.buildId(idAnnotation, fieldInfo);
                tableInfoImpl.setId(Caster.cast(fieldInfo));
            }
            Column column = getter.getAnnotation(Column.class);
            if (null != column) {
                if (StringUtil.isBlank(column.name())) {
                    fieldInfo.setDbFieldName(StringUtil.camel2Underline(getter.getName().substring(Constant.GET_METHOD_PREFIX.length()), JdbcConstant.DEFAULT_CASE));
                } else {
                    fieldInfo.setDbFieldName(column.name());
                    fieldInfo.setSpecified(true);
                }
                pojoFields.add(fieldInfo);
            }
            fieldInfo.initColumn(column);
            OneToMany oneToMany = getter.getAnnotation(OneToMany.class);
            if (null != oneToMany) {
                mappings.add(this.createMapping(oneToMany, pojoSubject.getPojoType(), fieldInfo));
            }
            OneToOne oneToOne = getter.getAnnotation(OneToOne.class);
            if (null != oneToOne) {
                mappings.add(this.createMapping(oneToOne, pojoSubject.getPojoType(), fieldInfo));
            }
            ManyToOne manyToOne = getter.getAnnotation(ManyToOne.class);
            if (null != manyToOne) {
                mappings.add(this.createMapping(manyToOne, pojoSubject.getPojoType(), fieldInfo));
            }
            LogicDelete logicDelete = getter.getAnnotation(LogicDelete.class);
            if (null != logicDelete) {
                this.setLogicDeleteField(logicDelete, fieldInfo, tableInfoImpl);
            }
        }
        // ????????????
        pojoFields.sort(Comparator.comparingInt(f -> f.getColumn().getSort()));
        Map<String, PojoField> fileInfoMap = new LinkedHashMap<>(pojoFields.size());
        for (PojoField pojoField : pojoFields) {
            fileInfoMap.put(pojoField.getJavaFieldName(), pojoField);
        }
        tableInfoImpl.setFieldInfoMap(fileInfoMap);
        // ????????????????????????
        mappings.sort(Comparator.comparing(PojoMapping::sort));
        tableInfoImpl.setMappings(mappings);
        tableInfoImpl.setTriggerInfoList(this.createTrigger(pojoSubject.getPojoType().getAnnotation(Pojo.class).trigger()));
    }

    /**
     * ????????????????????????????????????
     * @param getters pojo????????????getter??????
     * @param pojoType pojo??????
     */
    private void filterGetter(Set<MethodDescriptor> getters, Class<?> pojoType) {
        MethodDescriptor id = null;
        MethodDescriptor logicDeleteFiled = null;
        int logicDeleteFiledCount = 0;
        for (MethodDescriptor getter : getters) {
            if (getter.getMethod().getAnnotation(Id.class) != null) {
                id = getter;
            } else if (getter.getMethod().getAnnotation(LogicDelete.class) != null) {
                logicDeleteFiled = getter;
                logicDeleteFiledCount ++;
            }
        }
        if (null == id) {
            throw new UniqueKeyUndefinedException("pojo??????????????????" + pojoType.getName());
        }
        if (null == id.getMethod().getAnnotation(Column.class)) {
            throw new IllegalArgumentException("????????????????????????@Column?????????" + pojoType.getName() + "#" + id.getMethod().getName());
        }
        if (!id.getMethod().getAnnotation(Column.class).insertable()) {
            throw new IllegalArgumentException("?????????insertable?????????true???" + pojoType.getName() + "#" + id.getMethod().getName());
        }
        if (null != logicDeleteFiled) {
            if (logicDeleteFiledCount > 1) {
                throw new FieldAlreadyExistsException("????????????????????????????????????" + pojoType.getName() + "#" + logicDeleteFiled.getMethod().getName());
            }
            if (null == logicDeleteFiled.getMethod().getAnnotation(Column.class)) {
                throw new IllegalArgumentException("??????????????????????????????@Column?????????" + pojoType.getName() + "#" + logicDeleteFiled.getMethod().getName());
            }
        }
        getters.removeIf(g -> (g.getMethod().getAnnotation(Column.class) == null && g.getMethod().getAnnotation(OneToMany.class) == null
                && g.getMethod().getAnnotation(OneToOne.class) == null && g.getMethod().getAnnotation(ManyToOne.class) == null)
                || g.getMethod().getParameterTypes().length > 1);
    }

    /**
     * ??????getter???????????????
     * @param pojoType pojo??????
     * @param getter get??????
     */
    private void checkGetter(Class<?> pojoType, Method getter) {
        Column column = getter.getAnnotation(Column.class);
        OneToMany oneToMany = getter.getAnnotation(OneToMany.class);
        OneToOne oneToOne = getter.getAnnotation(OneToOne.class);
        ManyToOne manyToOne = getter.getAnnotation(ManyToOne.class);
        long count = Stream.of(column, oneToMany, oneToOne, manyToOne).filter(Objects::nonNull).count();
        if (count > 1) {
            throw new MutualExclusionException("Column OneToMany OneToOne ManyToOne?????????????????????????????????" + pojoType.getName() + "#" + getter.getName());
        }
    }

    /**
     * ??????getter?????????????????????????????????setter
     * @param pojoType pojo??????
     * @param getter getter??????
     * @param setters pojo????????????setter
     * @return ?????????getter????????????????????????setter
     */
    protected Method findSetterMethodByGetter(Class<?> pojoType, Method getter, Set<MethodDescriptor> setters) {
        String setterName = Constant.SET_METHOD_PREFIX + getter.getName().substring(Constant.GET_METHOD_PREFIX.length());
        MethodDescriptor target = null;
        for (MethodDescriptor setter : setters) {
            if (setterName.equals(setter.getName())) {
                Class<?> setterType = setter.getMethod().getParameterTypes()[0];
                boolean notMatch = setterType != getter.getReturnType() && !setterType.isAssignableFrom(getter.getReturnType());
                if (setter.getMethod().getParameterCount() > 1 || notMatch) {
                    throw new NotStandardSetterMethodException("set???????????????????????????setter getter??????????????????"+ pojoType.getName() + "#" + setter.getName());
                }
                target = setter;
                break;
            }
        }
        if (null == target) {
            throw new CannotFindSetterMethodException("?????????????????????set?????????" + pojoType.getName() + "#" + getter.getName() + "\t???????????????????????????????????????");
        }
        setters.remove(target);
        return target.getMethod();
    }

    /**
     * ?????????????????????
     * @param idAnnotation ????????????
     * @param fieldInfo ????????????
     */
    protected TableFieldInfo buildId(Id idAnnotation, TableFieldInfo fieldInfo) {
        if (idAnnotation.idGenerator().isInterface()) {
            throw new IllegalArgumentException("????????????????????????" + idAnnotation.idGenerator().getName());
        }
        IdFieldImpl id = new IdFieldImpl(fieldInfo.getGetter(), fieldInfo.getSetter(), Caster.cast(idAnnotation.idGenerator()));
        BeanUtils.copyProperties(fieldInfo, id);
        id.initParameter(idAnnotation.parameters());
        return id;
    }

    public void setLogicDeleteField(LogicDelete logicDelete, TableFieldInfo fieldInfo, TableInfoImpl tableInfoImpl) {
        LogicDeleteFieldImpl logicDeleteFieldImpl = new LogicDeleteFieldImpl(fieldInfo.getGetter(), fieldInfo.getSetter(), logicDelete.deleteValue(), logicDelete.normalValue());
        BeanUtils.copyProperties(fieldInfo, logicDeleteFieldImpl);
        tableInfoImpl.setLogicDeleteField(logicDeleteFieldImpl);
    }

    @SuppressWarnings("unchecked")
    protected PojoMapping createMapping(OneToMany oneToMany, Class<?> pojoType, PojoField pojoField) {
        if (null == oneToMany) {
            return null;
        }
        if (!Collection.class.isAssignableFrom(pojoField.getJavaType())) {
            throw new UnsupportedTypeException("??????????????????????????????" + pojoField.getGetter().getName() + "#" + pojoField.getJavaType().getName());
        }
        Class<?> rawClass = ResolvableType.forMethodReturnType(pojoField.getGetter()).getGeneric(0).getRawClass();
        if (null == rawClass) {
            throw new NullPointerException("?????????????????????" + pojoField.getGetter().getName());
        }
        return new OneToManyMappingImpl(rawClass, (Class<? extends Collection<?>>) pojoField.getJavaType(), pojoField, oneToMany)
                .addJoinCondition(oneToMany.condition(), pojoType, rawClass);
    }

    protected PojoMapping createMapping(OneToOne oneToOne, Class<?> pojoType, PojoField pojoField) {
        return new OneToOneMappingImpl(pojoField.getJavaType(), pojoField, oneToOne).addJoinCondition(oneToOne.condition(), pojoType, pojoField.getJavaType());
    }

    protected PojoMapping createMapping(ManyToOne manyToOne, Class<?> pojoType, PojoField pojoField) {
        return new ManyToOneMappingImpl(pojoField.getJavaType(), pojoField, manyToOne).addJoinCondition(manyToOne.condition(), pojoType, pojoField.getJavaType());
    }

    protected List<TriggerInfo> createTrigger(Trigger[] triggers) {
        if (CollectionUtil.isEmpty(triggers)) {
            return Collections.emptyList();
        }
        List<TriggerInfo> triggerInfoList = new ArrayList<>(triggers.length);
        List<Trigger> triggerList = Arrays.stream(triggers).sorted(Comparator.comparingInt(Trigger::sort)).collect(Collectors.toList());
        for (Trigger trigger : triggerList) {
            if (cool.lazy.cat.orm.core.jdbc.component.trigger.Trigger.class == trigger.type()) {
                throw new IllegalArgumentException("????????????????????????" + trigger.type().getName());
            }
            triggerInfoList.add(new TriggerInfoImpl(trigger));
        }
        triggerInfoList.sort(Comparator.comparingInt(TriggerInfo::getSort));
        return triggerInfoList;
    }

    /**
     * ???????????????????????????
     * @param pojoTableSubjectMap ??????pojo?????????
     */
    protected void initJoinCondition(Map<Class<?>, PojoTableSubject> pojoTableSubjectMap) {
        for (PojoTableSubject pojoTableSubject : pojoTableSubjectMap.values()) {
            for (PojoMapping mapping : pojoTableSubject.getTableInfo().getMappings()) {
                this.initJoinCondition(mapping, pojoTableSubjectMap);
            }
        }
    }

    /**
     * ??????????????????????????? ???????????????
     * @param pojoMapping ????????????
     * @param pojoTableSubjectMap ??????pojo?????????
     */
    protected void initJoinCondition(PojoMapping pojoMapping, Map<Class<?>, PojoTableSubject> pojoTableSubjectMap) {
        for (On condition : pojoMapping.getJoinCondition()) {
            String foreignKey = condition.getForeignField();
            String targetFiled = condition.getTargetFiled();
            Class<?> foreignPojoType = condition.getForeignPojoType();
            Class<?> targetPojoType = condition.getTargetPojoType();
            if (!pojoTableSubjectMap.containsKey(targetPojoType)) {
                throw new CannotFindJoinConditionException("????????????????????????????????????????????????"+ foreignPojoType.getName() + "#" + pojoMapping.getPojoField().getGetter().getName() + " -> #" + targetPojoType.getName() + "\t????????????????????????????????????@Pojo?????????");
            }
            PojoField foreignKeyFieldInfo = pojoTableSubjectMap.get(foreignPojoType).getTableInfo().getFieldInfoMap().get(foreignKey);
            if (null == foreignKeyFieldInfo) {
                throw new CannotFindJoinConditionException("???????????????????????????????????????" + foreignPojoType.getName() + "#" + foreignKey + "\t" + targetPojoType.getName() + "#" + targetFiled);
            }
            foreignKeyFieldInfo.setForeignKey(true);
            PojoField targetFieldInfo = pojoTableSubjectMap.get(targetPojoType).getTableInfo().getFieldInfoMap().get(targetFiled);
            if (null == targetFieldInfo) {
                throw new CannotFindJoinConditionException("???????????????????????????????????????" + foreignPojoType.getName() + "#" + foreignKey + "\t" + targetPojoType.getName() + "#" + targetFiled);
            }
            targetFieldInfo.setForeignKey(true);
            condition.setForeignKeyInfo(foreignKeyFieldInfo);
            condition.setTargetFiledInfo(targetFieldInfo);
            // ????????????????????????????????? a -> b || a <- b
            if (!pojoMapping.havingMappedToSource()) {
                switch (condition.getAssignmentMethod()) {
                    case MAPPED_TO_SOURCE:
                        condition.setMappedToSource(true);
                        break;
                    case SOURCE_TO_MAPPED:
                        condition.setMappedToSource(false);
                        break;
                    case INFER_SOURCE_TO_MAPPED:
                        condition.setMappedToSource(targetFieldInfo instanceof IdField);
                        break;
                    case INFER_MAPPED_TO_SOURCE:
                        if (targetFieldInfo instanceof IdField) {
                            condition.setMappedToSource(true);
                        } else {
                            condition.setMappedToSource(foreignKeyFieldInfo instanceof IdField);
                        }
                        break;
                }
                if (condition.isMappedToSource()) {
                    pojoMapping.setHavingMappedToSource(true);
                }
            }
        }
    }

    /**
     * ??????table????????????
     * @param allSubjectMap ??????pojo?????????
     */
    protected void analysisTableChain(Map<Class<?>, PojoTableSubject> allSubjectMap) {
        // ?????????FieldMapper
        for (PojoTableSubject subject : allSubjectMap.values()) {
            TableInfo tableInfo = subject.getTableInfo();
            PojoFieldMapperImpl pojoFieldMapperImpl = new PojoFieldMapperImpl(tableInfo);
            tableInfo.setFieldMapper(pojoFieldMapperImpl);
        }
        // ?????????????????????????????????
        for (PojoTableSubject subject : allSubjectMap.values()) {
            TableInfo tableInfo = subject.getTableInfo();
            PojoFieldMapper fieldMapper = tableInfo.getFieldMapper();
            Map<String, PojoFieldMapper> sourceStructure = new LinkedHashMap<>(tableInfo.getMappings().size());
            for (PojoMapping mapping : tableInfo.getMappings()) {
                sourceStructure.put(mapping.getPojoField().getJavaFieldName(), allSubjectMap.get(mapping.getJavaType()).getTableInfo().getFieldMapper());
            }
            fieldMapper.setSourceStructure(sourceStructure);
        }
        // ?????????????????????
        for (PojoTableSubject subject : allSubjectMap.values()) {
            TableInfo tableInfo = subject.getTableInfo();
            PojoFieldMapper fieldMapper = tableInfo.getFieldMapper();
            fieldMapper.setFieldAccessor(new CascadeSelfAdaptionFieldAccessor(tableInfo));
        }
    }
}
