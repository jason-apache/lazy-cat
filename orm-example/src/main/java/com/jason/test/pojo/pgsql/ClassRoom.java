package com.jason.test.pojo.pgsql;

import com.jason.test.constant.Constant;
import com.jason.test.annotation.DataSource;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.Entry;
import cool.lazy.cat.orm.api.web.entrust.method.QueryPageApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.RemoveCascadeApiEntry;
import cool.lazy.cat.orm.api.web.entrust.method.SaveCascadeApiEntry;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToMany;
import cool.lazy.cat.orm.core.base.annotation.OneToOne;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.base.annotation.Table;
import cool.lazy.cat.orm.core.jdbc.component.id.UUIdGenerator;
import lombok.Data;

import java.util.List;

/**
 * @author: mahao
 * @date: 2022-01-10 17:05
 */
@Data
@Pojo(table = @Table(schema = "jpa"))
@DataSource(id = Constant.LOCAL_PG)
@ApiPojo(entry = {
        @Entry(path = "queryPage", api = QueryPageApiEntry.class),
        @Entry(path = "save", api = SaveCascadeApiEntry.class),
        @Entry(path = "delete", api = RemoveCascadeApiEntry.class)
})
public class ClassRoom {

    private String id;
    private String code;
    private String grade;
    private String floor;
    private String classRoomExtId;
    private ClassRoomExt classRoomExt = new ClassRoomExt();
    private List<BlackBoard> blackBoards;

    @Id(idGenerator = UUIdGenerator.class)
    @Column
    public String getId() {
        return id;
    }

    @Column
    public String getCode() {
        return code;
    }

    @Column
    public String getGrade() {
        return grade;
    }

    @Column
    public String getFloor() {
        return floor;
    }

    @Column
    public String getClassRoomExtId() {
        return classRoomExtId;
    }

    @OneToOne(condition = {@On(foreignFiled = "classRoomExtId", targetFiled = "id")}, cascadeLevel = 3)
    public ClassRoomExt getClassRoomExt() {
        return classRoomExt;
    }

    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "classRoomId")})
    public List<BlackBoard> getBlackBoards() {
        return blackBoards;
    }
}
