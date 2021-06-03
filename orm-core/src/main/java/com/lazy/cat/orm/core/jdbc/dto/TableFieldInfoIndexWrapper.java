package com.lazy.cat.orm.core.jdbc.dto;


import com.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

/**
 * @author: mahao
 * @date: 2021/3/20 18:01
 * 列映射关系，协助反射对象的赋值
 */
public class TableFieldInfoIndexWrapper {

    /**
     * 当前字段对应resultSet结果集的第几列
     */
    private int index;
    /**
     * 如果是一个映射对象中的属性，标注这个对象位于表链的位置
     */
    private int chainFlatIndex = -1;
    /**
     * 字段信息
     */
    private TableFieldInfo fieldInfo;

    public int getIndex() {
        return index;
    }

    public TableFieldInfoIndexWrapper setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getChainFlatIndex() {
        return chainFlatIndex;
    }

    public TableFieldInfoIndexWrapper setChainFlatIndex(int chainFlatIndex) {
        this.chainFlatIndex = chainFlatIndex;
        return this;
    }

    public TableFieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public TableFieldInfoIndexWrapper setFieldInfo(TableFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        return this;
    }
}
