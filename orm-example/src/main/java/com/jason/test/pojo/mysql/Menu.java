package com.jason.test.pojo.mysql;

import com.jason.test.base.BasePojo;
import com.jason.test.convert.BasicBooleanTypeConverter;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.ApiQueryFilter;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Parameter;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.AllLike;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/11/11 16:37
 */
@Setter
@Accessors(chain = true)
@Pojo
@ApiPojo(nameSpace = "menu", entry = {
        @Entry(path = "/select", api = QueryApiEntry.class),
        @Entry(path = "/delete", api = RemoveApiEntry.class),
        @Entry(path = "/save", api = SaveApiEntry.class),
})
public class Menu extends BasePojo {
    private static final long serialVersionUID = -6270739481865620430L;

    private String id;
    private String name;
    private String url;
    private Boolean locked;

    @Override
    @Column
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "menu_id_seq")})
    public String getId() {
        return id;
    }

    @Column
    @ApiQueryFilter(In.class)
    public String getName() {
        return name;
    }

    @Column
    @ApiQueryFilter(AllLike.class)
    public String getUrl() {
        return url;
    }

    @Column(typeConverter = BasicBooleanTypeConverter.class)
    public Boolean getLocked() {
        return locked;
    }
}
