package com.jason.test.base;

import com.lazy.cat.orm.core.base.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: mahao
 * @date: 2021/3/3 18:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TreePojo extends RecordPojo {
    private static final long serialVersionUID = -1684724096453254860L;

    private String path;
    private Integer level;

    public abstract Serializable getParentId();

    public abstract TreePojo getParent();

    @Column
    public String getPath() {
        return path;
    }

    @Column
    public Integer getLevel() {
        return level;
    }
}
