package com.jason.test.pojo.mysql;

import com.jason.test.base.BasePojo;
import cool.lazy.cat.orm.annotation.Column;
import cool.lazy.cat.orm.annotation.Id;
import cool.lazy.cat.orm.annotation.On;
import cool.lazy.cat.orm.annotation.OneToMany;
import cool.lazy.cat.orm.annotation.Parameter;
import cool.lazy.cat.orm.annotation.Pojo;
import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.jdbc.component.id.SequenceIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/12 11:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Pojo
public class FtpDir extends BasePojo {

    private static final long serialVersionUID = -6899858483663807824L;
    private String id;
    private String userId;
    private String name;
    private List<FtpFile> ftpFileList;

    @Override
    @Column(updatable = false)
    @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "ftp_dir_id_seq")})
    public String getId() {
        return id;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    @Column
    public String getName() {
        return name;
    }

    @OneToMany(condition = {
            @On(foreignFiled = "id", targetFiled = "dirId"),
            @On(foreignFiled = "userId", targetFiled = "userId")
    })
    public List<FtpFile> getFtpFileList() {
        return ftpFileList;
    }
}
