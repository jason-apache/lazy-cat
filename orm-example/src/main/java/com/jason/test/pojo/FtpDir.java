package com.jason.test.pojo;

import com.jason.test.base.BasePojo;
import com.lazy.cat.orm.core.base.annotation.Column;
import com.lazy.cat.orm.core.base.annotation.Id;
import com.lazy.cat.orm.core.base.annotation.On;
import com.lazy.cat.orm.core.base.annotation.OneToMany;
import com.lazy.cat.orm.core.base.annotation.Pojo;
import com.lazy.cat.orm.core.jdbc.component.id.Auto;
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
    @Id(idGenerator = Auto.class)
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
    }, deletable = true)
    public List<FtpFile> getFtpFileList() {
        return ftpFileList;
    }
}
