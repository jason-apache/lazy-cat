package com.jason.test.pojo.pgsql;

import com.jason.test.annotation.DataSource;
import com.jason.test.constant.Constant;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.base.component.id.UUIdGenerator;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2022-01-11 13:49
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@Accessors(chain = true)
@DataSource(id = Constant.LOCAL_PG)
public class Subject {

    private String id;
    private String name;
    private String subjectExtId;
    private SubjectExt subjectExt = new SubjectExt();

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
    public String getSubjectExtId() {
        return subjectExtId;
    }

    @OneToOne(condition = {@On(foreignFiled = "subjectExtId", targetFiled = "id")})
    public SubjectExt getSubjectExt() {
        return subjectExt;
    }
}
