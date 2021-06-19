package com.jason.test.pojo;


import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.constant.ApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToMany;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;
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
        @Entry(path = "/example/selectPage/", mappingApi = ApiEntry.QUERY_PAGE),
        @Entry(path = "/example/select/", mappingApi = ApiEntry.QUERY),
        @Entry(path = "/example/save/", mappingApi = ApiEntry.SAVE),
        @Entry(path = "/example/saveForce/", mappingApi = ApiEntry.SAVE_CASCADE),
        @Entry(path = "/example/delete/", mappingApi = ApiEntry.REMOVE),
        @Entry(path = "/example/deleteForce/", mappingApi = ApiEntry.REMOVE_CASCADE)
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
    @Id(idGenerator = Auto.class)
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
    }, updatable = false, insertable = false)
    public UserDir getDir() {
        return dir;
    }

    public UserFile setDir(UserDir dir) {
        this.dir = dir;
        return this;
    }

    @Column(queryFilter = ConditionType.ALL_LIKE)
    public String getFileName() {
        return fileName;
    }

    @OneToMany(condition = {
            @On(foreignFiled = "id", targetFiled = "fileId"),
            @On(foreignFiled = "dirId", targetFiled = "dirId"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    }, cascadeLevel = 4, deletable = true)
    public List<FileContent> getFileContentList() {
        return fileContentList;
    }

    @Column
    public String getUserId() {
        return userId;
    }
}
