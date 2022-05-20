package com.jason.test.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jason.test.convert.BasicBooleanTypeConverter;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.LogicDelete;
import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.core.jdbc.component.convert.OracleLocalDateTimeConverter;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.Equals;
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

    @Column(sort = Integer.MAX_VALUE -70)
    public String getCreateBy() {
        return createBy;
    }

    @Column(typeConverter = OracleLocalDateTimeConverter.class, sort = Integer.MAX_VALUE -60)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Column(sort = Integer.MAX_VALUE -50)
    public String getUpdateBy() {
        return updateBy;
    }

    @Column(typeConverter = OracleLocalDateTimeConverter.class, sort = Integer.MAX_VALUE -40)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Column(sort = Integer.MAX_VALUE -30)
    public String getRemark() {
        return remark;
    }

    @LogicDelete(deleteValue = "1", normalValue = "0")
    @Column(typeConverter = BasicBooleanTypeConverter.class, sort = Integer.MAX_VALUE -20)
    @ApiQueryFilter(Equals.class)
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Column(sort = Integer.MAX_VALUE -10)
    public Long getModifyCounter() {
        return modifyCounter;
    }
}
