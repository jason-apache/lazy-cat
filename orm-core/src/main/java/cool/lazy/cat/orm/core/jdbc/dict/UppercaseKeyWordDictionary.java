package cool.lazy.cat.orm.core.jdbc.dict;

import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;

import javax.annotation.PostConstruct;

/**
 * @author: mahao
 * @date: 2021/3/10 15:11
 * 关键字字典
 */
public class UppercaseKeyWordDictionary implements KeywordDictionary {

    public static UppercaseKeyWordDictionary instance;

    @PostConstruct
    private void init() {
        instance = this;
    }

    @Override
    public String call() {
        return JdbcConstant.CALL;
    }

    @Override
    public String select() {
        return JdbcConstant.SELECT;
    }

    @Override
    public String delete() {
        return JdbcConstant.DELETE;
    }

    @Override
    public String update() {
        return JdbcConstant.UPDATE;
    }

    @Override
    public String from() {
        return JdbcConstant.FROM;
    }

    @Override
    public String where() {
        return JdbcConstant.WHERE;
    }

    @Override
    public String and() {
        return JdbcConstant.AND;
    }

    @Override
    public String or() {
        return JdbcConstant.OR;
    }

    @Override
    public String on() {
        return JdbcConstant.ON;
    }

    @Override
    public String left() {
        return JdbcConstant.LEFT;
    }

    @Override
    public String right() {
        return JdbcConstant.RIGHT;
    }

    @Override
    public String inner() {
        return JdbcConstant.INNER;
    }

    @Override
    public String join() {
        return JdbcConstant.JOIN;
    }

    @Override
    public String order() {
        return JdbcConstant.ORDER;
    }

    @Override
    public String by() {
        return JdbcConstant.BY;
    }

    @Override
    public String group() {
        return JdbcConstant.GROUP;
    }

    @Override
    public String count() {
        return JdbcConstant.COUNT;
    }

    @Override
    public String set() {
        return JdbcConstant.SET;
    }

    @Override
    public String as() {
        return JdbcConstant.AS;
    }

    @Override
    public String like() {
        return JdbcConstant.LIKE;
    }

    @Override
    public String in() {
        return JdbcConstant.IN;
    }

    @Override
    public String is() {
        return JdbcConstant.IS;
    }

    @Override
    public String not() {
        return JdbcConstant.NOT;
    }

    @Override
    public String nul() {
        return JdbcConstant.NULL;
    }

    @Override
    public String limit() {
        return JdbcConstant.LIMIT;
    }

    @Override
    public String asc() {
        return JdbcConstant.ASC;
    }

    @Override
    public String desc() {
        return JdbcConstant.DESC;
    }

    @Override
    public String insert() {
        return JdbcConstant.INSERT;
    }

    @Override
    public String into() {
        return JdbcConstant.INTO;
    }

    @Override
    public String values() {
        return JdbcConstant.VALUES;
    }

    @Override
    public String concat() {
        return JdbcConstant.CONCAT;
    }
}
