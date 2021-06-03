package com.lazy.cat.orm.core.jdbc.mapping;


import com.lazy.cat.orm.core.base.annotation.Sequence;

/**
 * @author: mahao
 * @date: 2021/4/23 19:48
 * 序列信息
 */
public class SequenceInfo {

    /**
     * 序列所在的库
     */
    private String schema;
    /**
     * 序列名称
     */
    private String name;

    public SequenceInfo(Sequence sequence) {
        this.schema = sequence.schema();
        this.name = sequence.name();
    }

    public String getSchema() {
        return schema;
    }

    public SequenceInfo setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getName() {
        return name;
    }

    public SequenceInfo setName(String name) {
        this.name = name;
        return this;
    }
}
