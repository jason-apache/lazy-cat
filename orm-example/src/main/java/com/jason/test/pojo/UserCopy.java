package com.jason.test.pojo;

import com.jason.test.trigger.RecordPojoTrigger;
import com.jason.test.validator.RegexValidator;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Sequence;
import cool.lazy.cat.orm.core.base.annotation.Trigger;
import cool.lazy.cat.orm.core.base.annotation.Validator;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/3/30 21:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Pojo(trigger = {@Trigger(type = RecordPojoTrigger.class)})
@ApiPojo(nameSpace = "user2", entry = {
        @Entry(path = "/example/selectPage/", api = QueryPageApiEntry.class),
        @Entry(path = "/example/select/", api = QueryApiEntry.class),
        @Entry(path = "/example/save/", api = SaveApiEntry.class),
        @Entry(path = "/example/saveForce/", api = SaveCascadeApiEntry.class),
        @Entry(path = "/example/delete/", api = RemoveApiEntry.class),
        @Entry(path = "/example/deleteForce/", api = RemoveCascadeApiEntry.class)
})
public class UserCopy extends User {
    private static final long serialVersionUID = 7252431295211904669L;

    private String id;
    private String name;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = SequenceIdGenerator.class, sequence = @Sequence(schema = "${core}", name = "user_id_seq"))
    public String getId() {
        return id;
    }

    @Override
    @Column(
            queryFilter = ConditionType.IN,
            notNull = true, minLength = 2, minLengthErrorMsg = "名称不能小于两位", maxLength = 40, maxLengthErrorMsg = "名称不能大于40个汉字"
    )
    public String getName() {
        return this.name;
    }

    @Override
    public UserCopy setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    @Column
    public Integer getAge() {
        return super.getAge();
    }

    @Override
    @Column(/*validator = @Validator(type = SexValidator.class, errorMsg = "Ladies only!")*/)
    public String getSex() {
        return super.getSex();
    }

    @Override
    @Column(validator = @Validator(type = RegexValidator.class,
            validateContent = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$",
            errorMsg = "无效手机号！"))
    public String getPhoneNum() {
        return super.getPhoneNum();
    }
}
