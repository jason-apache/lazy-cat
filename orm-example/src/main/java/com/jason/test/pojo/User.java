package com.jason.test.pojo;


import com.jason.test.base.RecordPojo;
import com.jason.test.trigger.RecordPojoTrigger;
import com.jason.test.validator.RegexValidator;
import com.lazy.cat.orm.api.web.annotation.ApiPojo;
import com.lazy.cat.orm.api.web.annotation.Entry;
import com.lazy.cat.orm.api.web.constant.ApiEntry;
import com.lazy.cat.orm.core.base.annotation.Column;
import com.lazy.cat.orm.core.base.annotation.Id;
import com.lazy.cat.orm.core.base.annotation.ManyToOne;
import com.lazy.cat.orm.core.base.annotation.On;
import com.lazy.cat.orm.core.base.annotation.OneToMany;
import com.lazy.cat.orm.core.base.annotation.Pojo;
import com.lazy.cat.orm.core.base.annotation.Sequence;
import com.lazy.cat.orm.core.base.annotation.Trigger;
import com.lazy.cat.orm.core.base.annotation.Validator;
import com.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import com.lazy.cat.orm.core.jdbc.condition.ConditionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/2/13 14:09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiPojo(nameSpace = "user", entry = {
        @Entry(path = "/example/selectPage/", mappingApi = ApiEntry.QUERY_PAGE),
        @Entry(path = "/example/select/", mappingApi = ApiEntry.QUERY),
        @Entry(path = "/example/save/", mappingApi = ApiEntry.SAVE),
        @Entry(path = "/example/saveForce/", mappingApi = ApiEntry.SAVE_CASCADE),
        @Entry(path = "/example/delete/", mappingApi = ApiEntry.REMOVE),
        @Entry(path = "/example/deleteForce/", mappingApi = ApiEntry.REMOVE_CASCADE)
})
@Pojo(trigger = @Trigger(type = RecordPojoTrigger.class))
public class User extends RecordPojo {

    private static final Long serialVersionUID = 2242091901887685062L;
    private String id;
    private String name;
    private Integer age;
    private String education;
    private String sex;
    private String phoneNum;
    private String department;
    private Office office;
    private List<UserDir> userDirList;
    private List<FtpDir> ftpDirList;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = SequenceIdGenerator.class, sequence = @Sequence(schema = "${core}", name = "user_id_seq"))
    public String getId() {
        return id;
    }

    @Column(queryFilter = ConditionType.IN)
    public String getName() {
        return name;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    @Column
    public String getEducation() {
        return education;
    }

    @Column
    public String getSex() {
        return sex;
    }

    @Column(validator = @Validator(type = RegexValidator.class,
            validateContent = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$",
            errorMsg = "无效手机号！"))
    public String getPhoneNum() {
        return phoneNum;
    }

    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, cascadeLevel = 3, deletable = true)
    public List<UserDir> getUserDirList() {
        return userDirList;
    }

    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, cascadeLevel = 2, deletable = true)
    public List<FtpDir> getFtpDirList() {
        return ftpDirList;
    }

    @Column
    public String getDepartment() {
        return department;
    }

    @ManyToOne(condition = @On(foreignFiled = "department", targetFiled = "id"), updatable = false, insertable = false)
    public Office getOffice() {
        return office;
    }
}

