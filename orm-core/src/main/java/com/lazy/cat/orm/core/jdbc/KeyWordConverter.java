package com.lazy.cat.orm.core.jdbc;

import com.lazy.cat.orm.core.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: mahao
 * @date: 2021/3/10 15:11
 * 关键字转换器
 */
public class KeyWordConverter {

    protected boolean uppercase;

    @Autowired
    private void initUppercase(JdbcConfig jdbcConfig){
        this.uppercase = jdbcConfig.isUppercase();
    }

    /**
     * 将驼峰规范字符串转换为下划线
     * @param word 字符串
     * @return 以下划线区分单词的字符串
     */
    public String toDbWord(String word) {
        return StringUtil.camel2Underline(word, !uppercase);
    }

    /**
     * 将以下划线区分单词的字符串转换为小驼峰命名规范的字符串
     * @param word 字符串
     * @return 小驼峰命名规范的字符串
     */
    public String toJavaWord(String word) {
        return StringUtil.underline2Camel(word, true);
    }

    public String call() {
        return uppercase ? JdbcConstant.CALL : JdbcConstant.CALL.toLowerCase();
    }

    public String select() {
        return uppercase ? JdbcConstant.SELECT : JdbcConstant.SELECT.toLowerCase();
    }

    public String delete() {
        return uppercase ? JdbcConstant.DELETE : JdbcConstant.DELETE.toLowerCase();
    }

    public String update() {
        return uppercase ? JdbcConstant.UPDATE : JdbcConstant.UPDATE.toLowerCase();
    }

    public String inert() {
        return uppercase ? JdbcConstant.INSERT + JdbcConstant.INTO : (JdbcConstant.INSERT + JdbcConstant.INTO).toLowerCase();
    }

    public String from() {
        return uppercase ? JdbcConstant.FROM : JdbcConstant.FROM.toLowerCase();
    }

    public String where() {
        return uppercase ? JdbcConstant.WHERE : JdbcConstant.WHERE.toLowerCase();
    }

    public String and() {
        return uppercase ? JdbcConstant.AND : JdbcConstant.AND.toLowerCase();
    }

    public String or() {
        return uppercase ? JdbcConstant.OR : JdbcConstant.OR.toLowerCase();
    }

    public String on() {
        return uppercase ? JdbcConstant.ON : JdbcConstant.ON.toLowerCase();
    }

    public String left() {
        return uppercase ? JdbcConstant.LEFT : JdbcConstant.LEFT.toLowerCase();
    }

    public String right() {
        return uppercase ? JdbcConstant.RIGHT : JdbcConstant.RIGHT.toLowerCase();
    }

    public String join() {
        return uppercase ? JdbcConstant.JOIN : JdbcConstant.JOIN.toLowerCase();
    }

    public String order() {
        return uppercase ? JdbcConstant.ORDER : JdbcConstant.ORDER.toLowerCase();
    }

    public String by() {
        return uppercase ? JdbcConstant.BY : JdbcConstant.BY.toLowerCase();
    }

    public String group() {
        return uppercase ? JdbcConstant.GROUP : JdbcConstant.GROUP.toLowerCase();
    }

    public String count() {
        return uppercase ? JdbcConstant.COUNT : JdbcConstant.COUNT.toLowerCase();
    }

    public String set() {
        return uppercase ? JdbcConstant.SET : JdbcConstant.SET .toLowerCase();
    }

    public String as() {
        return uppercase ? JdbcConstant.AS : JdbcConstant.AS .toLowerCase();
    }

    public String like() {
        return uppercase ? JdbcConstant.LIKE : JdbcConstant.LIKE.toLowerCase();
    }

    public String in() {
        return uppercase ? JdbcConstant.IN : JdbcConstant.IN.toLowerCase();
    }

    public String is() {
        return uppercase ? JdbcConstant.IS : JdbcConstant.IS.toLowerCase();
    }

    public String not() {
        return uppercase ? JdbcConstant.NOT : JdbcConstant.NOT.toLowerCase();
    }

    public String nul() {
        return uppercase ? JdbcConstant.NULL : JdbcConstant.NULL.toLowerCase();
    }

    public String limit() {
        return uppercase ? JdbcConstant.LIMIT : JdbcConstant.LIMIT.toLowerCase();
    }

    public String asc() {
        return uppercase ? JdbcConstant.ASC : JdbcConstant.ASC.toLowerCase();
    }

    public String desc() {
        return uppercase ? JdbcConstant.DESC : JdbcConstant.DESC.toLowerCase();
    }

    public String insert() {
        return uppercase ? JdbcConstant.INSERT : JdbcConstant.INSERT.toLowerCase();
    }

    public String into() {
        return uppercase ? JdbcConstant.INTO : JdbcConstant.INTO.toLowerCase();
    }

    public String value() {
        return uppercase ? JdbcConstant.VALUE : JdbcConstant.VALUE.toLowerCase();
    }

    public String values() {
        return uppercase ? JdbcConstant.VALUES : JdbcConstant.VALUES.toLowerCase();
    }
}
