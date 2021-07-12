package com.jason.test.pojo;

import com.jason.test.base.TreePojo;
import com.jason.test.trigger.TreePojoTrigger;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveByIdsApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToMany;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Trigger;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/24 09:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiPojo(entry = {
        @Entry(path = "/example/selectPage/", api = QueryPageApiEntry.class),
        @Entry(path = "/example/select/", api = QueryApiEntry.class),
        @Entry(path = "/example/save/", api = SaveApiEntry.class),
        @Entry(path = "/example/saveForce/", api = SaveCascadeApiEntry.class),
        @Entry(path = "/example/delete/", api = RemoveApiEntry.class),
        @Entry(path = "/example/deleteForce/", api = RemoveCascadeApiEntry.class),
        @Entry(path = "/example/deleteByIds/", api = RemoveByIdsApiEntry.class)
})
@Pojo(trigger = @Trigger(type = TreePojoTrigger.class))
public class Office extends TreePojo {
    private static final long serialVersionUID = 4313014002861820050L;

    private String id;
    private String code;
    private String name;
    private String parentId;
    private Office parent;
    private List<Office> childrenList;

    @Override
    @Id(idGenerator = UUIdGenerator.class)
    @Column(updatable = false)
    public String getId() {
        return id;
    }

    @Column(queryFilter = ConditionType.IN)
    public String getCode() {
        return code;
    }

    @Column(queryFilter = ConditionType.ALL_LIKE)
    public String getName() {
        return name;
    }

    @Override
    @Column
    public String getParentId() {
        return parentId;
    }

    @Override
    @ManyToOne(condition = @On(foreignFiled = "parentId", targetFiled = "id"), insertable = false, updatable = false)
    public Office getParent() {
        return parent;
    }

    @OneToMany(condition = @On(foreignFiled = "id", targetFiled = "parentId"), deletable = true)
    public List<Office> getChildrenList() {
        return childrenList;
    }
}
