package cool.lazy.cat.orm.api.util;

import cool.lazy.cat.orm.api.manager.ApiPojoManager;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.manager.subject.ApiQueryFilterInfo;
import cool.lazy.cat.orm.core.base.util.ReflectUtil;
import cool.lazy.cat.orm.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.constant.ConditionConstant;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldDescriptor;
import cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition;
import cool.lazy.cat.orm.base.jdbc.sql.condition.type.ConditionType;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.None;
import cool.lazy.cat.orm.core.manager.PojoTableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/7/26 14:38
 */
public final class ConditionHelper {

    private ConditionHelper() {}

    /**
     * 将map转换为SqlCondition, 使用ApiQueryFilter注解中的queryFilter条件and拼接
     * @see cool.lazy.cat.orm.api.base.anno.ApiQueryFilter#value()
     * @param apiPojoManager apiPojo管理器
     * @param paramMap map查询参数
     * @return sqlCondition对象
     */
    public static SqlCondition convert(Class<?> pojoType, ApiPojoManager apiPojoManager, Map<String, Object> paramMap) {
        if (null == paramMap || paramMap.isEmpty()) {
            return null;
        }
        SqlConditionImpl root = null;
        // 字段访问器
        FieldAccessor fieldAccessor = PojoTableManager.getDefaultInstance().getByPojoType(pojoType).getTableInfo().getFieldMapper().getFieldAccessor();
        fieldAccessor.init();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            if (StringUtil.isEmpty(key)) {
                continue;
            }
            // key = pojo字段名称 找到这个名称对应的字段 再得到这个字段归属的Pojo类型
            FieldDescriptor fieldDescriptor = fieldAccessor.get(key);
            ApiPojoSubject belongApiPojo = apiPojoManager.getByPojoType(fieldDescriptor.getTableNode().getPojoType());
            String javaFieldName = fieldDescriptor.getJavaFieldName();
            // 从对应的pojo类型找到对应的apiPojo和@ApiQueryFilter注解
            ApiQueryFilterInfo apiQueryFilterInfo = belongApiPojo.getQueryFilterInfoMap().get(javaFieldName);
            if (null == apiQueryFilterInfo) {
                continue;
            }
            Class<? extends ConditionType> conditionType = apiQueryFilterInfo.getQueryFilterType();
            if (conditionType == None.class || null == conditionType) {
                continue;
            }
            ConditionType conditionTypeInstance = ConditionConstant.CONDITION_TYPE_CACHE.computeIfAbsent(conditionType, v -> (ConditionType) ReflectUtil.newInstance(conditionType));
            if (null == root) {
                root = new SqlConditionImpl(key, entry.getValue(), conditionTypeInstance);
            } else {
                root.and(new SqlConditionImpl(key, entry.getValue(), conditionTypeInstance));
            }
        }
        return root;
    }

    private static class SqlConditionImpl implements SqlCondition {

        private final String field;
        private final Object value;
        private final ConditionType type;
        private List<SqlCondition> and;
        private List<SqlCondition> or;

        public SqlConditionImpl(String field, Object value, ConditionType type) {
            this.field = field;
            this.value = value;
            this.type = type;
        }

        @Override
        public String field() {
            return field;
        }

        @Override
        public Object value() {
            return value;
        }

        @Override
        public ConditionType type() {
            return type;
        }

        @Override
        public SqlCondition and(SqlCondition condition) {
            if (null == this.and) {
                this.and = new ArrayList<>();
            }
            and.add(condition);
            return this;
        }

        @Override
        public SqlCondition or(SqlCondition condition) {
            if (null == this.or) {
                this.or = new ArrayList<>();
            }
            or.add(condition);
            return this;
        }

        @Override
        public List<SqlCondition> getAnd() {
            return and;
        }

        @Override
        public List<SqlCondition> getOr() {
            return or;
        }

        @Override
        public List<SqlCondition> flatCondition() {
            return and;
        }
    }
}
