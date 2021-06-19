package com.jason.test.pojo;

import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.core.base.annotation.Column;
import cool.lazy.cat.orm.core.base.annotation.Id;
import cool.lazy.cat.orm.core.base.annotation.ManyToOne;
import cool.lazy.cat.orm.core.base.annotation.On;
import cool.lazy.cat.orm.core.base.annotation.OneToOne;
import cool.lazy.cat.orm.core.base.annotation.Pojo;
import cool.lazy.cat.orm.core.jdbc.component.id.Auto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: mahao
 * @date: 2021/3/12 10:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Pojo
public class FileContent extends BasePojo {

    private static final long serialVersionUID = 7865288469070837075L;
    private String id;
    private String content;
    private String suffix;
    private String fileId;
    private UserFile file;
    private String dirId;
    private UserDir dir;
    private String userId;
    private User user;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = Auto.class)
    public String getId() {
        return id;
    }

    @Column
    public String getContent() {
        return content;
    }

    @Column
    public String getSuffix() {
        return suffix;
    }

    @Column
    public String getFileId() {
        return fileId;
    }

    @ManyToOne(condition = {
            @On(foreignFiled = "fileId", targetFiled = "id"),
            @On(foreignFiled = "dirId", targetFiled = "dirId"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    }, updatable = false, insertable = false)
    public UserFile getFile() {
        return file;
    }

    @Column
    public String getDirId() {
        return dirId;
    }

    @ManyToOne(condition = {@On(foreignFiled = "dirId", targetFiled = "id")}, updatable = false, insertable = false)
    public UserDir getDir() {
        return dir;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    @OneToOne(condition = {@On(foreignFiled = "userId", targetFiled = "id")}, updatable = false, insertable = false)
    public User getUser() {
        return user;
    }
}
