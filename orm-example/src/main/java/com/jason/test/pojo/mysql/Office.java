package com.jason.test.pojo.mysql;

import com.jason.test.base.NodePojo;
import com.jason.test.trigger.TreePojoTrigger;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.ApiQueryFilter;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveByIdsApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToMany;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Trigger;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.Equals;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/24 09:35
 */
@Setter
@Accessors(chain = true)
@ToString(exclude = {"parent", "childrenList"})
@ApiPojo(entry = {
        @Entry(path = "select/", api = QueryApiEntry.class),
        @Entry(path = "save/", api = SaveApiEntry.class),
        @Entry(path = "delete/", api = RemoveApiEntry.class),
        @Entry(path = "deleteForce/", api = RemoveCascadeApiEntry.class),
        @Entry(path = "deleteByIds/", api = RemoveByIdsApiEntry.class)
})
@Pojo(trigger = @Trigger(type = TreePojoTrigger.class))
public class Office extends NodePojo {
    private static final long serialVersionUID = 4313014002861820050L;

    private String id;
    private String code;
    private String name;
    private Office parent;
    private List<Office> childrenList;
    private List<User> userList;

    @Override
    @Id(idGenerator = UUIdGenerator.class)
    @Column(updatable = false)
    public String getId() {
        return id;
    }

    @Column
    @ApiQueryFilter(value = In.class)
    public String getCode() {
        return code;
    }

    @Column
    @ApiQueryFilter(In.class)
    public String getName() {
        return name;
    }

    @Override
    @ManyToOne(condition = @On(foreignFiled = "parentId", targetFiled = "id"))
    public Office getParent() {
        return parent;
    }

    @OneToMany(condition = @On(foreignFiled = "id", targetFiled = "parentId"), cascadeScope = {"childrenList.childrenList"})
    @Override
    public List<Office> getChildrenList() {
        return childrenList;
    }

    @OneToMany(condition = @On(foreignFiled = "id", targetFiled = "department"), cascadeLevel = 1)
    public List<User> getUserList() {
        return userList;
    }

    @Override
    @Column
    @ApiQueryFilter(Equals.class)
    public Integer getLevel() {
        return super.getLevel();
    }
}
