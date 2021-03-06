package com.jason.test.pojo.pgsql;

import com.jason.test.annotation.DataSource;
import com.jason.test.constant.Constant;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.base.component.id.UUIdGenerator;
import lombok.Data;

/**
 * @author: mahao
 * @date: 2022-01-10 17:06
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@DataSource(id = Constant.LOCAL_PG)
public class TableChair {

    private String id;
    private String color;
    private String crafts;
    private String weight;
    private Long studentId;

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getColor() {
        return color;
    }

    @Column
    public String getCrafts() {
        return crafts;
    }

    @Column
    public String getWeight() {
        return weight;
    }

    @Column
    public Long getStudentId() {
        return studentId;
    }
}
