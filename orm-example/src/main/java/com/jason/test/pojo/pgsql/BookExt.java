package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
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
public class BookExt {

    private String id;
    private String thickness;
    private String length;
    private String weight;
    private String subjectId;
    private Subject subject;

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getThickness() {
        return thickness;
    }

    @Column
    public String getLength() {
        return length;
    }

    @Column
    public String getWeight() {
        return weight;
    }

    @Column
    public String getSubjectId() {
        return subjectId;
    }

    @ManyToOne(condition = {@On(foreignFiled = "subjectId", targetFiled = "id")}, insertable = true, updatable = true)
    public Subject getSubject() {
        return subject;
    }
}
