package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import lombok.Data;

/**
 * @author: mahao
 * @date: 2022-01-11 14:30
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@DataSource(id = Constant.LOCAL_PG)
public class Department {

    private String id;
    private String name;
    private String departmentExtId;
    private DepartmentExt departmentExt = new DepartmentExt();

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public String getDepartmentExtId() {
        return departmentExtId;
    }

    @OneToOne(condition = {@On(foreignFiled = "departmentExtId", targetFiled = "id")})
    public DepartmentExt getDepartmentExt() {
        return departmentExt;
    }
}
