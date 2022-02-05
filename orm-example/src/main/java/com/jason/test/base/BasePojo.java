package com.jason.test.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/3 18:02
 */
public abstract class BasePojo implements Serializable {

    private static final long serialVersionUID = 2612504805890870242L;
    private transient boolean isNewRecord;

    public abstract Serializable getId();

    @JsonIgnore
    public boolean isNewRecord() {
        return isNewRecord;
    }
    @JsonIgnore
    public void setIsNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    @Override
    public boolean equals(Object that) {
        if (null == that) {
            return false;
        }
        if (that.getClass() != getClass()) {
            return false;
        }
        if (null == ((BasePojo) that).getId()) {
            return false;
        }
        return Objects.equals(this.getId(), ((BasePojo) that).getId());
    }

    @Override
    public int hashCode() {
        return null == getId() ? 0 : getId().hashCode() * 31;
    }
}
