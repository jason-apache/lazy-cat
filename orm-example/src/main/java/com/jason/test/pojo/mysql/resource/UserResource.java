package com.jason.test.pojo.mysql.resource;

import com.jason.test.constant.Constant;
import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/11/4 16:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Pojo
public class UserResource extends BasePojo {
    private static final long serialVersionUID = 7065468643366053274L;

    private Long id;
    private String userId;
    private Long resourceId;
    private Resource resource;

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "user_resource_id_seq")})
    @Column(updatable = false)
    @Override
    public Long getId() {
        return id;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    @Column
    public Long getResourceId() {
        return resourceId;
    }

    @ManyToOne(condition = @On(foreignFiled = "resourceId", targetFiled = "id"), updatable = true, insertable = true, deletable = true)
    public Resource getResource() {
        return resource;
    }
}
