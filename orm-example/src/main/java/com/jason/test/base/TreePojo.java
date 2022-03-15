package com.jason.test.base;

import cool.lazy.cat.orm.annotation.Column;
import lombok.Setter;

/**
 * @author: mahao
 * @date: 2021/3/3 18:04
 */
@Setter
public abstract class TreePojo extends RecordPojo {
    private static final long serialVersionUID = -1684724096453254860L;

    private Object parentId;
    private String path;
    private Integer level;

    public abstract TreePojo getParent();

    @Column
    public Object getParentId() {
        return parentId;
    }

    @Column
    public String getPath() {
        return path;
    }

    @Column
    public Integer getLevel() {
        return level;
    }
}
