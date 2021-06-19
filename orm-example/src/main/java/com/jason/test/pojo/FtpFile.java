package com.jason.test.pojo;

import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.constant.ApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/3/12 11:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiPojo(entry = {
        @Entry(path = "/example/selectPage/", mappingApi = ApiEntry.QUERY_PAGE),
        @Entry(path = "/example/select/", mappingApi = ApiEntry.QUERY),
        @Entry(path = "/example/save/", mappingApi = ApiEntry.SAVE),
        @Entry(path = "/example/saveForce/", mappingApi = ApiEntry.SAVE_CASCADE),
        @Entry(path = "/example/delete/", mappingApi = ApiEntry.REMOVE),
        @Entry(path = "/example/deleteForce/", mappingApi = ApiEntry.REMOVE_CASCADE)
})
@Pojo
public class FtpFile extends BasePojo {
    private static final long serialVersionUID = 5088576773964954813L;

    private String id;
    private String name;
    private String dirId;
    private FtpDir dir;
    private String userId;
    private User user;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = Auto.class)
    public String getId() {
        return id;
    }

    @Column(queryFilter = ConditionType.EQUALS)
    public String getName() {
        return name;
    }

    @Column
    public String getDirId() {
        return dirId;
    }

    @ManyToOne(condition = {
            @On(foreignFiled = "dirId", targetFiled = "id"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    }, updatable = false, insertable = false)
    public FtpDir getDir() {
        return dir;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    @ManyToOne(condition = {@On(foreignFiled = "userId", targetFiled = "id")}, updatable = false, insertable = false)
    public User getUser() {
        return user;
    }
}
