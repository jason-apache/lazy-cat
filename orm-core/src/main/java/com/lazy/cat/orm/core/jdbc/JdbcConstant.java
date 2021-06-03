package com.lazy.cat.orm.core.jdbc;


import com.lazy.cat.orm.core.jdbc.dialect.type.DatabaseType;
import com.lazy.cat.orm.core.jdbc.dialect.type.DatabaseTypeImpl;

/**
 * @author: mahao
 * @date: 2021/3/10 13:48
 */
public class JdbcConstant {

    public static final String MAIN_TABLE_NAME = "this_";
    public static final String COUNT_TABLE_NAME = "count_";
    public static final String LIMITER_TABLE_NAME = "limiter_";
    public static final String CALL = "CALL ";
    public static final String SELECT = "SELECT ";
    public static final String DELETE = "DELETE ";
    public static final String UPDATE = "UPDATE ";
    public static final String INSERT = "INSERT ";
    public static final String INTO = "INTO ";
    public static final String FROM = "FROM ";
    public static final String WHERE = "WHERE ";
    public static final String AND = "AND ";
    public static final String OR = "OR ";
    public static final String ON = "ON ";
    public static final String LEFT = "LEFT ";
    public static final String RIGHT = "RIGHT ";
    public static final String JOIN = "JOIN ";
    public static final String ORDER = "ORDER ";
    public static final String BY = "BY ";
    public static final String GROUP = "GROUP ";
    public static final String COUNT = "COUNT";
    public static final String SET = "SET ";
    public static final String AS = "AS ";
    public static final String LIKE = "LIKE ";
    public static final String IN = "IN ";
    public static final String NOT = "NOT ";
    public static final String IS = "IS ";
    public static final String NULL = "NULL ";
    public static final String LIMIT = "LIMIT ";
    public static final String ASC = "ASC ";
    public static final String DESC = "DESC ";
    public static final String VALUE = "VALUE ";
    public static final String VALUES = "VALUES ";

    public static final int MAX_TABLE_NAME_LENGTH = 14;
    public static final int MAX_FIELD_NAME_LENGTH = 15;
    public static final int DEFAULT_CONTAINER_SIZE = 1000;
    public static final int TABLE_CHAIN_WARN_COUNT = 40;

    public static final DatabaseType DATABASE_TYPE_ORACLE = new DatabaseTypeImpl("oracle");
    public static final DatabaseType DATABASE_TYPE_MYSQL = new DatabaseTypeImpl("mysql");
}
