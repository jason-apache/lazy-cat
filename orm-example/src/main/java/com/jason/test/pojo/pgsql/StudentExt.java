package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2022-01-10 17:06
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@Accessors(chain = true)
@DataSource(id = Constant.LOCAL_PG)
public class StudentExt {

    private String id;
    private String hobby;
    private String beGoodAt;

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getHobby() {
        return hobby;
    }

    @Column
    public String getBeGoodAt() {
        return beGoodAt;
    }
}
