package com.jason.test.pojo.mysql;

import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.api.web.annotation.ApiPojo;
import cool.lazy.cat.orm.api.web.annotation.ApiQueryFilter;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.ManyToOne;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToOne;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import cool.lazy.cat.orm.core.jdbc.sql.condition.type.like.RightLike;
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
@ApiPojo(entry = {})
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
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "file_content_id_seq")})
    public String getId() {
        return id;
    }

    @Column
    @ApiQueryFilter(RightLike.class)
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
    })
    public UserFile getFile() {
        return file;
    }

    @Column
    public String getDirId() {
        return dirId;
    }

    @ManyToOne(condition = {@On(foreignFiled = "dirId", targetFiled = "id")})
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
