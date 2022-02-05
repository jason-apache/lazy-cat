package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import lombok.Data;

/**
 * @author: mahao
 * @date: 2022-01-10 17:08
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@DataSource(id = Constant.LOCAL_PG)
public class BlackBoard {

    private String id;
    private String length;
    private String specification;
    private String manufacturingCompany;
    private String classRoomId;

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getLength() {
        return length;
    }

    @Column
    public String getSpecification() {
        return specification;
    }

    @Column
    public String getManufacturingCompany() {
        return manufacturingCompany;
    }

    @Column
    public String getClassRoomId() {
        return classRoomId;
    }
}
