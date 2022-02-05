package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToOne;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2022-01-10 17:13
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@Accessors(chain = true)
@DataSource(id = Constant.LOCAL_PG)
public class Book {

    private String id;
    private String name;
    private String press;
    private String price;
    private Long studentId;
    private String bookExtId;
    private BookExt bookExt = new BookExt();

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
    public String getPress() {
        return press;
    }

    @Column
    public String getPrice() {
        return price;
    }

    @Column
    public Long getStudentId() {
        return studentId;
    }

    @Column
    public String getBookExtId() {
        return bookExtId;
    }

    @OneToOne(condition = {@On(foreignFiled = "bookExtId", targetFiled = "id")})
    public BookExt getBookExt() {
        return bookExt;
    }
}
