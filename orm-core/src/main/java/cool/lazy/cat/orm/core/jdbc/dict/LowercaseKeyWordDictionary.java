package cool.lazy.cat.orm.core.jdbc.dict;

import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;

/**
 * @author: mahao
 * @date: 2021/3/10 15:11
 * 关键字字典
 */
public class LowercaseKeyWordDictionary implements KeywordDictionary {

    @Override
    public String call() {
        return JdbcConstant.CALL_LOWER;
    }

    @Override
    public String select() {
        return JdbcConstant.SELECT_LOWER;
    }

    @Override
    public String delete() {
        return JdbcConstant.DELETE_LOWER;
    }

    @Override
    public String update() {
        return JdbcConstant.UPDATE_LOWER;
    }

    @Override
    public String from() {
        return JdbcConstant.FROM_LOWER;
    }

    @Override
    public String where() {
        return JdbcConstant.WHERE_LOWER;
    }

    @Override
    public String and() {
        return JdbcConstant.AND_LOWER;
    }

    @Override
    public String or() {
        return JdbcConstant.OR_LOWER;
    }

    @Override
    public String on() {
        return JdbcConstant.ON_LOWER;
    }

    @Override
    public String left() {
        return JdbcConstant.LEFT_LOWER;
    }

    @Override
    public String right() {
        return JdbcConstant.RIGHT_LOWER;
    }

    @Override
    public String inner() {
        return JdbcConstant.INNER_LOWER;
    }

    @Override
    public String join() {
        return JdbcConstant.JOIN_LOWER;
    }

    @Override
    public String order() {
        return JdbcConstant.ORDER_LOWER;
    }

    @Override
    public String by() {
        return JdbcConstant.BY_LOWER;
    }

    @Override
    public String group() {
        return JdbcConstant.GROUP_LOWER;
    }

    @Override
    public String count() {
        return JdbcConstant.COUNT_LOWER;
    }

    @Override
    public String set() {
        return JdbcConstant.SET_LOWER;
    }

    @Override
    public String as() {
        return JdbcConstant.AS_LOWER;
    }

    @Override
    public String like() {
        return JdbcConstant.LIKE_LOWER;
    }

    @Override
    public String in() {
        return JdbcConstant.IN_LOWER;
    }

    @Override
    public String is() {
        return JdbcConstant.IS_LOWER;
    }

    @Override
    public String not() {
        return JdbcConstant.NOT_LOWER;
    }

    @Override
    public String nul() {
        return JdbcConstant.NULL_LOWER;
    }

    @Override
    public String limit() {
        return JdbcConstant.LIMIT_LOWER;
    }

    @Override
    public String asc() {
        return JdbcConstant.ASC_LOWER;
    }

    @Override
    public String desc() {
        return JdbcConstant.DESC_LOWER;
    }

    @Override
    public String insert() {
        return JdbcConstant.INSERT_LOWER;
    }

    @Override
    public String into() {
        return JdbcConstant.INTO_LOWER;
    }

    @Override
    public String values() {
        return JdbcConstant.VALUES_LOWER;
    }

    @Override
    public String concat() {
        return JdbcConstant.CONCAT_LOWER;
    }
}
