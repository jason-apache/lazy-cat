package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import lombok.Data;

/**
 * @author: mahao
 * @date: 2022-01-10 17:07
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@DataSource(id = Constant.LOCAL_PG)
public class ClassRoomExt {

    private String id;
    private Integer studentCount;
    private String departmentId;
    private Department department;

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public Integer getStudentCount() {
        return studentCount;
    }

    @Column
    public String getDepartmentId() {
        return departmentId;
    }

    @ManyToOne(condition = {@On(foreignFiled = "departmentId", targetFiled = "id")}, insertable = true, updatable = true)
    public Department getDepartment() {
        return department;
    }
}
