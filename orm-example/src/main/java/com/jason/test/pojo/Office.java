package com.jason.test.pojo;

import com.jason.test.base.TreePojo;
import com.jason.test.trigger.TreePojoTrigger;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.constant.ApiEntry;
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
        @Entry(path = "/example/selectPage/", mappingApi = ApiEntry.QUERY_PAGE),
        @Entry(path = "/example/select/", mappingApi = ApiEntry.QUERY),
        @Entry(path = "/example/save/", mappingApi = ApiEntry.SAVE),
        @Entry(path = "/example/saveForce/", mappingApi = ApiEntry.SAVE_CASCADE),
        @Entry(path = "/example/delete/", mappingApi = ApiEntry.REMOVE),
        @Entry(path = "/example/deleteForce/", mappingApi = ApiEntry.REMOVE_CASCADE),
        @Entry(path = "/example/deleteByIds/", mappingApi = ApiEntry.REMOVE_BY_IDS)
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
