package com.lazy.cat.orm.core.jdbc.condition;


import com.lazy.cat.orm.core.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/16 15:13
 * sql条件
 */
public class Condition {

    private final String field;
    private final Object value;
    private final ConditionType type;
    /**
     * 嵌套的and关系条件
     */
    private List<Condition> and;
    /**
     * 嵌套的or关系条件
     */
    private List<Condition> or;

    public static final Condition EMPTY_CONDITION = new Condition(null, null, null);

    private Condition(String field, Object value, ConditionType type) {
        this.field = field;
        this.value = value;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public ConditionType getType() {
        return type;
    }

    public List<Condition> getAnd() {
        return and;
    }

    public Condition setAnd(List<Condition> and) {
        this.and = and;
        return this;
    }

    public List<Condition> getOr() {
        return or;
    }

    public Condition setOr(List<Condition> or) {
        this.or = or;
        return this;
    }

    private static void check(String field, Object value) {
        if (StringUtil.isBlank(field) || null == value) {
            throw new NullPointerException("condition参数不能为空");
        }
    }

    public Condition and(Condition condition) {
        if (null == condition) {
            return this;
        }
        if (this.and == null) {
            this.and = new ArrayList<>();
        }
        this.and.add(condition);
        return this;
    }

    public Condition or(Condition condition) {
        if (null == condition) {
            return this;
        }
        if (this.or == null) {
            this.or = new ArrayList<>();
        }
        this.or.add(condition);
        return this;
    }

    public static Condition eq(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.EQUALS);
    }

    public static Condition neq(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.NOT_EQUALS);
    }

    public static Condition like(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.ALL_LIKE);
    }

    public static Condition leftLike(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.LEFT_LIKE);
    }

    public static Condition rightLike(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.RIGHT_LIKE);
    }

    public static Condition isNull(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.IS_NULL);
    }

    public static Condition notNull(String field) {
        check(field, "");
        return new Condition(field, null, ConditionType.NOT_NULL);
    }

    public static Condition in(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.IN);
    }

    public static Condition notIn(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.NOT_IN);
    }

    public static Condition gt(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.GREATER_THAN);
    }

    public static Condition gte(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.GREATER_THAN_EQUALS);
    }

    public static Condition lt(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.LESS_THAN);
    }

    public static Condition lte(String field, Object value) {
        check(field, value);
        return new Condition(field, value, ConditionType.LESS_THAN_EQUALS);
    }
}
