package com.jason.test.pojo.mysql;


import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToMany;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.api.base.anno.ApiPojo;
import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.api.base.anno.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.AllLike;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/11 17:58
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
@Pojo
public class UserFile extends BasePojo {

    private static final long serialVersionUID = -2164038043339154611L;
    private String id;
    private String dirId;
    private UserDir dir;
    private String userId;
    private String fileName;
    private List<FileContent> fileContentList;

    @Override
    @Column
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "user_file_id_seq")})
    public String getId() {
        return id;
    }

    @Column
    public String getDirId() {
        return dirId;
    }

    @ManyToOne(condition = {
            @On(foreignFiled = "dirId", targetFiled = "id"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    })
    public UserDir getDir() {
        return dir;
    }

    public UserFile setDir(UserDir dir) {
        this.dir = dir;
        return this;
    }

    @Column
    @ApiQueryFilter(AllLike.class)
    public String getFileName() {
        return fileName;
    }

    @OneToMany(condition = {
            @On(foreignFiled = "id", targetFiled = "fileId"),
            @On(foreignFiled = "dirId", targetFiled = "dirId"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    }, cascadeLevel = 4)
    public List<FileContent> getFileContentList() {
        return fileContentList;
    }

    @Column
    public String getUserId() {
        return userId;
    }
}
