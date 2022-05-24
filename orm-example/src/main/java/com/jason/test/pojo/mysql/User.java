package com.jason.test.pojo.mysql;


import com.jason.test.api.TestApiEntry;
import com.jason.test.base.RecordPojo;
import com.jason.test.component.conditiontype.InOrIsNull;
import com.jason.test.constant.Sex;
import com.jason.test.constant.ValidateConstant;
import com.jason.test.pojo.mysql.resource.UserResource;
import com.jason.test.trigger.RecordPojoTrigger;
import com.jason.test.validator.CommonValidator;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToMany;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.annotation.Trigger;
import cool.lazy.cat.orm.annotation.Validator;
import cool.lazy.cat.orm.api.base.anno.ApiPojo;
import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.api.base.anno.Entry;
import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.convert.SimpleEnumTypeConverter;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.Equals;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.In;
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
        @Entry(path = "selectPage", api = QueryPageApiEntry.class, methods = {HttpMethod.POST}),
        @Entry(path = "selectPage", api = QueryPageApiEntry.class, methods = {HttpMethod.GET}),
        @Entry(path = "select", api = QueryApiEntry.class),
        @Entry(path = "save", api = SaveApiEntry.class),
        @Entry(path = "saveForce", api = SaveCascadeApiEntry.class),
        @Entry(path = "delete", api = RemoveApiEntry.class, methods = HttpMethod.POST),
        @Entry(path = "delete", api = RemoveApiEntry.class, methods = {HttpMethod.DELETE, HttpMethod.GET}),
        @Entry(path = "deleteForce", api = RemoveCascadeApiEntry.class),
        @Entry(path = "test", api = TestApiEntry.class)
})
@Pojo(trigger = @Trigger(type = RecordPojoTrigger.class), table = @Table(schema = "m1"))
public class User extends RecordPojo {

    private static final Long serialVersionUID = 2242091901887685062L;
    private String id;
    private String name;
    private Integer age;
    private String education;
    private Sex sex;
    private String phoneNum;
    private String department;
    private Office office;
    private List<UserDir> userDirList;
    private List<FtpDir> ftpDirList;
    private List<UserResource> resourceList;

    @Override
    @Column(updatable = false, sort = 1)
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "user_id_seq")})
    @ApiQueryFilter(In.class)
    public String getId() {
        return id;
    }

    @Column(sort = 10)
    @ApiQueryFilter(In.class)
    public String getName() {
        return name;
    }

    @Column(validator = @Validator(type = CommonValidator.class, notNull = true, parameter = {@Parameter(name = CommonValidator.VALIDATE_INFO_KEY, value = ValidateConstant.VALIDATE_HUMAN_AGE_KEY)}),
            sort = 20)
    @ApiQueryFilter(Equals.class)
    public Integer getAge() {
        return age;
    }

    @Column(sort = 30)
    public String getEducation() {
        return education;
    }

    @Column(sort = 40, typeConverter = SimpleEnumTypeConverter.class)
    @ApiQueryFilter(Equals.class)
    public Sex getSex() {
        return sex;
    }

    @Column(validator = @Validator(type = CommonValidator.class,
            parameter = {@Parameter(name = CommonValidator.VALIDATE_INFO_KEY, value = ValidateConstant.VALIDATE_USER_PHONE_NUM_KEY)}),
            sort = 50)
    public String getPhoneNum() {
        return phoneNum;
    }

    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")},
            cascadeScope = {"userDirList.userFileList.fileContentList"},
            ignoreFields = {"userDirList.userFileList.fileContentList.suffix"},
            sort = 10)
    public List<UserDir> getUserDirList() {
        return userDirList;
    }

    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, cascadeLevel = 2, sort = 20)
    public List<FtpDir> getFtpDirList() {
        return ftpDirList;
    }

    @Column(sort = 60)
    @ApiQueryFilter(InOrIsNull.class)
    public String getDepartment() {
        return department;
    }

    @ManyToOne(condition = @On(foreignFiled = "department", targetFiled = "id"), sort = 30, cascadeScope = {"office.parent"})
    public Office getOffice() {
        return office;
    }

    @OneToMany(condition = @On(foreignFiled = "id", targetFiled = "userId"), cascadeLevel = 2, sort = 40)
    public List<UserResource> getResourceList() {
        return resourceList;
    }
}

