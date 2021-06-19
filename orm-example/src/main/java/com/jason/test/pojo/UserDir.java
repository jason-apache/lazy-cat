package com.jason.test.pojo;


import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.constant.ApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToMany;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
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
        @Entry(path = "/example/selectPage/", mappingApi = ApiEntry.QUERY_PAGE),
        @Entry(path = "/example/select/", mappingApi = ApiEntry.QUERY),
        @Entry(path = "/example/save/", mappingApi = ApiEntry.SAVE),
        @Entry(path = "/example/saveForce/", mappingApi = ApiEntry.SAVE_CASCADE),
        @Entry(path = "/example/delete/", mappingApi = ApiEntry.REMOVE),
        @Entry(path = "/example/deleteForce/", mappingApi = ApiEntry.REMOVE_CASCADE)
})
@Pojo
public class UserDir extends BasePojo {

    private static final long serialVersionUID = -819489892370177122L;
    private String id;
    private String userId;
    private String name;
    private List<UserFile> userFileList;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = Auto.class)
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
    }, deletable = true)
    public List<UserFile> getUserFileList() {
        return userFileList;
    }
}
