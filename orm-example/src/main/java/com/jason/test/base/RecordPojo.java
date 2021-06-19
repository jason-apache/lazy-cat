package com.jason.test.base;

import com.jason.test.convert.BooleanTypeConverter;
import com.jason.test.convert.OracleLocalDateTimeConverter;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.LogicDelete;
import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author: mahao
 * @date: 2021/3/4 17:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RecordPojo extends BasePojo {

    private static final long serialVersionUID = -5901585374183121679L;
    private String createBy;
    private LocalDateTime createDate;
    private String updateBy;
    private LocalDateTime updateDate;
    private String remark;
    private Boolean isDeleted;
    private Long modifyCounter;

    @Column
    public String getCreateBy() {
        return createBy;
    }

    @Column(typeConverter = OracleLocalDateTimeConverter.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Column
    public String getUpdateBy() {
        return updateBy;
    }

    @Column(typeConverter = OracleLocalDateTimeConverter.class)
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    @LogicDelete(deleteValue = "1", normalValue = "0")
    @Column(typeConverter = BooleanTypeConverter.class, queryFilter = ConditionType.EQUALS)
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Column
    public Long getModifyCounter() {
        return modifyCounter;
    }
}
