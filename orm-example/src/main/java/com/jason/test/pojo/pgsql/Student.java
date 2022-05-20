package com.jason.test.pojo.pgsql;

import com.jason.test.annotation.DataSource;
import com.jason.test.constant.Constant;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToMany;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.annotation.Table;
import cool.lazy.cat.orm.api.base.anno.ApiPojo;
import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.api.base.anno.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.AllLike;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: mahao
 * @date: 2022-01-10 17:05
 */
@Data
@Accessors(chain = true)
@Pojo(table = @Table(schema = "jpa"))
@DataSource(id = Constant.LOCAL_PG)
@ApiPojo(entry = {
        @Entry(path = "queryPage", api = QueryPageApiEntry.class),
        @Entry(path = "save", api = SaveCascadeApiEntry.class),
        @Entry(path = "delete", api = RemoveCascadeApiEntry.class)
})
public class Student {
    private Long id;
    private String classRoomId;
    private ClassRoom classRoom;
    private String studentExtId;
    private StudentExt studentExt = new StudentExt();
    private TableChair tableChair;
    private List<Book> books;
    private String name;
    private Integer age;
    private String sex;
    private LocalDateTime createDate;

    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "jpa"), @Parameter(name = Constant.SEQUENCE_NAME, value = "stu_id_seq")})
    @Column
    public Long getId() {
        return id;
    }

    @Column
    public String getClassRoomId() {
        return classRoomId;
    }

    @ManyToOne(condition = {@On(foreignFiled = "classRoomId", targetFiled = "id")}, updatable = true, cascadeLevel = 4, sort = 3)
    public ClassRoom getClassRoom() {
        return classRoom;
    }

    @Column
    public String getStudentExtId() {
        return studentExtId;
    }

    @OneToOne(condition = {@On(foreignFiled = "studentExtId", targetFiled = "id")}, sort = 2)
    public StudentExt getStudentExt() {
        return studentExt;
    }

    @OneToOne(condition = {@On(foreignFiled = "id", targetFiled = "studentId")}, sort = 1)
    public TableChair getTableChair() {
        return tableChair;
    }

    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "studentId")}, cascadeLevel = 4, sort = 4)
    public List<Book> getBooks() {
        return books;
    }

    @Column
    @ApiQueryFilter(AllLike.class)
    public String getName() {
        return name;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    @Column
    public String getSex() {
        return sex;
    }

    @Column
    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
