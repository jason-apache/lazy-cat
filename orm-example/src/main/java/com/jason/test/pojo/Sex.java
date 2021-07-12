package com.jason.test.pojo;


import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveByIdsApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/3/3 17:55
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Pojo
@ApiPojo(entry = {
        @Entry(path = "/example/deleteByIds/", api = RemoveByIdsApiEntry.class)
})
public class Sex extends BasePojo {

    private static final long serialVersionUID = -5149241407996403564L;
    private Long id;
    private String sex;
    private Integer sort;

    @Override
    @Id(idGenerator = Auto.class)
    @Column(updatable = false)
    public Long getId() {
        return id;
    }
}
