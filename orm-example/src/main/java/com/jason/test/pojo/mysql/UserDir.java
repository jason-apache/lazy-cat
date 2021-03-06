package com.jason.test.pojo.mysql;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToMany;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.api.base.anno.ApiPojo;
import cool.lazy.cat.orm.api.base.anno.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/9 12:18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiPojo(entry = {
        @Entry(path = "/example/selectPage/", api = QueryPageApiEntry.class),
        @Entry(path = "/example/select/", api = QueryApiEntry.class),
        @Entry(path = "/example/save/", api = SaveApiEntry.class),
        @Entry(path = "/example/saveForce/", api = SaveCascadeApiEntry.class),
        @Entry(path = "/example/delete/", api = RemoveApiEntry.class),
        @Entry(path = "/example/deleteForce/", api = RemoveCascadeApiEntry.class)
})
@Pojo(table = @Table(schema = "m1"))
public class UserDir extends BasePojo {

    private static final long serialVersionUID = -819489892370177122L;
    private String id;
    private String userId;
    private String name;
    private List<UserFile> userFileList;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "user_dir_id_seq")})
    public String getId() {
        return id;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    @Column
    public String getName() {
        return name;
    }

    @OneToMany(condition = {
            @On(foreignFiled = "id", targetFiled = "dirId"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    })
    @JsonIgnoreProperties(value = "dir")
    public List<UserFile> getUserFileList() {
        return userFileList;
    }
}
