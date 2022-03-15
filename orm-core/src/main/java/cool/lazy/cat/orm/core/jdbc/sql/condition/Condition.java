package cool.lazy.cat.orm.core.jdbc.sql.condition;


import cool.lazy.cat.orm.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.constant.ConditionConstant;
import cool.lazy.cat.orm.core.jdbc.sql.condition.inject.SqlConditionValuePlaceHolder;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.ConditionType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/16 15:13
 * sql条件
 */
public class Condition implements SqlCondition {

    private final String field;
    private final Object value;
    private final ConditionType type;
    /**
     * 嵌套的and关系条件
     */
    private List<SqlCondition> and;
    /**
     * 嵌套的or关系条件
     */
    private List<SqlCondition> or;
    private List<SqlCondition> flatCondition;

    private Condition(String field, Object value, ConditionType type) {
        check(field);
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
    public List<SqlCondition> getAnd() {
        return and;
    }

    @Override
    public List<SqlCondition> getOr() {
        return or;
    }

    private static void check(String field) {
        if (StringUtil.isBlank(field)) {
            throw new NullPointerException("condition参数不能为空");
        }
    }

    @Override
    public Condition and(SqlCondition condition) {
        if (null == condition) {
            return this;
        }
        if (this.and == null) {
            this.and = new ArrayList<>();
        }
        this.and.add(condition);
        return this;
    }

    @Override
    public Condition or(SqlCondition condition) {
        if (null == condition) {
            return this;
        }
        if (this.or == null) {
            this.or = new ArrayList<>();
        }
        this.or.add(condition);
        return this;
    }

    @Override
    public List<SqlCondition> flatCondition() {
        if (null != flatCondition) {
            return flatCondition;
        }
        List<SqlCondition> result = new ArrayList<>();
        result.add(this);
        if (null != and) {
            this.recursionAdd(and, result);
        } else if (null != or) {
            this.recursionAdd(or, result);
        }
        this.flatCondition = result;
        return result;
    }

    private void recursionAdd(List<SqlCondition> conditions, List<SqlCondition> result) {
        for (SqlCondition condition : conditions) {
            result.add(condition);
            if (null != condition.getAnd()) {
                this.recursionAdd(condition.getAnd(), result);
            }
            if (null != condition.getOr()) {
                this.recursionAdd(condition.getOr(), result);
            }
        }
    }

    /**
     * 属性相等条件 a = 'a'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition eq(String field, Object value) {
        return new Condition(field, value, ConditionConstant.EQUALS);
    }

    public static Condition eq(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.EQUALS);
    }

    /**
     * 属性不相等条件 a != 'a'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition neq(String field, Object value) {
        return new Condition(field, value, ConditionConstant.NOT_EQUALS);
    }

    public static Condition neq(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.NOT_EQUALS);
    }

    /**
     * 模糊匹配 通配符两端开放 a like '%a%'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition like(String field, Object value) {
        return new Condition(field, value, ConditionConstant.LIKE);
    }

    public static Condition like(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.LIKE);
    }

    /**
     * 模糊匹配 通配符在左 a like '%a'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition leLike(String field, Object value) {
        return new Condition(field, value, ConditionConstant.LEFT_LIKE);
    }

    public static Condition leLike(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.LEFT_LIKE);
    }

    /**
     * 模糊匹配 通配符在右 a like 'a%'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition riLike(String field, Object value) {
        return new Condition(field, value, ConditionConstant.RIGHT_LIKE);
    }

    public static Condition riLike(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.RIGHT_LIKE);
    }

    /**
     * 模糊匹配取反 通配符两端开放 a not like '%a%'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition notLike(String field, Object value) {
        return new Condition(field, value, ConditionConstant.NOT_LIKE);
    }

    public static Condition notLike(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.NOT_LIKE);
    }

    /**
     * 模糊匹配取反 通配符在左 a not like '%a'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition notLeLike(String field, Object value) {
        return new Condition(field, value, ConditionConstant.NOT_LEFT_LIKE);
    }

    public static Condition notLeLike(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.NOT_LEFT_LIKE);
    }

    /**
     * 模糊匹配取反 通配符在右 a not like 'a%'
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition notRiLike(String field, Object value) {
        return new Condition(field, value, ConditionConstant.NOT_RIGHT_LIKE);
    }

    public static Condition notRiLike(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.NOT_RIGHT_LIKE);
    }

    /**
     * 字段为空 a is null
     * @param field 字段名称
     * @return sql条件
     */
    public static Condition isNull(String field) {
        return new Condition(field, null, ConditionConstant.IS_NULL);
    }

    /**
     * 字段不为空 a is not null
     * @param field 字段名称
     * @return sql条件
     */
    public static Condition notNull(String field) {
        return new Condition(field, null, ConditionConstant.NOT_NULL);
    }

    /**
     * 字段在列表 a in ('a', 'b')
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition in(String field, Object value) {
        return new Condition(field, value, ConditionConstant.IN);
    }

    public static Condition in(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.IN);
    }

    /**
     * 字段不在列表 a not in ('a', 'b')
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition notIn(String field, Object value) {
        return new Condition(field, value, ConditionConstant.NOT_IN);
    }

    public static Condition notIn(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.NOT_IN);
    }

    /**
     * 字段大于 a > 22
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition gt(String field, Object value) {
        return new Condition(field, value, ConditionConstant.GREATER_THAN);
    }

    public static Condition gt(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.GREATER_THAN);
    }

    /**
     * 字段大于等于 a >= 22
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition gte(String field, Object value) {
        return new Condition(field, value, ConditionConstant.GREATER_THAN_EQUALS);
    }

    public static Condition gte(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.GREATER_THAN_EQUALS);
    }

    /**
     * 字段小于 a < 22
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition lt(String field, Object value) {
        return new Condition(field, value, ConditionConstant.LESS_THAN);
    }

    public static Condition lt(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.LESS_THAN);
    }

    /**
     * 字段小于等于 a <= 22
     * @param field 字段名称
     * @param value 值
     * @return sql条件
     */
    public static Condition lte(String field, Object value) {
        return new Condition(field, value, ConditionConstant.LESS_THAN_EQUALS);
    }

    public static Condition lte(String field, SqlConditionValuePlaceHolder sqlConditionValuePlaceHolder) {
        return new Condition(field, sqlConditionValuePlaceHolder, ConditionConstant.LESS_THAN_EQUALS);
    }
}
