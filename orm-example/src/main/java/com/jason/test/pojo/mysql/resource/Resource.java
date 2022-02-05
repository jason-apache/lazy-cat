package com.jason.test.pojo.mysql.resource;

import com.jason.test.constant.Constant;
import com.jason.test.base.RecordPojo;
import com.jason.test.trigger.RecordPojoTrigger;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Parameter;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Trigger;
import cool.lazy.cat.orm.core.jdbc.component.convert.SimpleEnumTypeConverter;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/11/4 16:40
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Pojo(trigger = @Trigger(type = RecordPojoTrigger.class))
@ApiPojo(entry = {
        @Entry(path = "query", api = QueryApiEntry.class),
        @Entry(path = "save", api = SaveApiEntry.class),
        @Entry(path = "delete", api = RemoveApiEntry.class),
})
public class Resource extends RecordPojo {
    private static final long serialVersionUID = -8846167862179805649L;

    private Long id;
    private String resourceName;
    private String title;
    private String desc;
    private ResourceType type;

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "resource_id_seq")})
    @Column(updatable = false)
    @Override
    public Long getId() {
        return id;
    }

    @Column
    public String getResourceName() {
        return resourceName;
    }

    @Column
    public String getTitle() {
        return title;
    }

    @Column
    public String getDesc() {
        return desc;
    }

    @Column(typeConverter = SimpleEnumTypeConverter.class)
    public ResourceType getType() {
        return type;
    }
}
