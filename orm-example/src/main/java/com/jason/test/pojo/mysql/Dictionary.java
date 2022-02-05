package com.jason.test.pojo.mysql;

import com.jason.test.base.RecordPojo;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Parameter;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: mahao
 * @date: 2021-11-17 14:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Pojo
public class Dictionary extends RecordPojo {
    private static final long serialVersionUID = -895241528995219906L;

    private String id;
    private String name;
    private String code;

    @Override
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "dictionary_id_seq")})
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public String getCode() {
        return code;
    }
}
