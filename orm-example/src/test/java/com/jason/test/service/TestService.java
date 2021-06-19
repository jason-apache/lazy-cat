package com.jason.test.service;

import com.jason.test.TestConfiguration;
import com.jason.test.pojo.Sex;
import com.jason.test.pojo.User;
import com.jason.test.pojo.UserCopy;
import com.jason.test.pojo.UserDir;
import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author: mahao
 * @date: 2021/4/14 21:06
 */
@SpringBootTest(classes = TestConfiguration.class)
public class TestService {

    @Autowired
    BaseService<User> baseService;
    @Autowired
    UserService userService;

    @Test
    public void testBuildSearchParam() {
        SearchParam<User> userSearchParam = baseService.buildSearchParam(User.class);
        SearchParam<Sex> sexSearchParam = baseService.buildSearchParam(Sex.class);
    }

    @Test
    public void testSelectByParam() {
        Collection<User> users = baseService.selectByParam(baseService.buildSearchParam(User.class));
        Collection<UserDir> userDirs = baseService.selectByParam(UserDir.class, baseService.buildSearchParam(UserDir.class));
    }

    @Test
    public void testSelectPageByParam() {
        // 一对多查询时忽略多的一方的查询，否则分页将失效
        PageResult<User> userPageResult = baseService.selectPageByParam(baseService.buildSearchParam(User.class)
                .setPageSize(150).setIgnorer(Ignorer.build("userDirList", "ftpDirList")));
        PageResult<UserDir> userDirPageResult = baseService.selectPageByParam(UserDir.class, baseService.buildSearchParam(UserDir.class).setPageSize(5));
    }

    @Test
    public void testSelect() {
        Collection<User> select = baseService.select(new QueryInfo());
        FullAutoMappingContext.setPojoType(UserDir.class);
        Collection objects = baseService.select(new QueryInfo());
    }

    @Test
    public void testSelectPage() {
        PageResult<User> userPageResult = baseService.selectPage(new QueryInfo().setIgnoreFields(new String[]{"userDirList", "ftpDirList"}).setPageSize(150));
        FullAutoMappingContext.setPojoType(UserDir.class);
        PageResult objects = baseService.selectPage(new QueryInfo());
    }

    @Test
    public void testSelectById() {
        User user = baseService.selectById(1);
        UserDir userDir = baseService.selectById(UserDir.class, 1);
    }

    @Test
    public void testSelectByIds() {
        Collection<User> users = baseService.selectByIds(Arrays.asList(1, 2, 3, "2591"));
        Collection<UserDir> userDirs = baseService.selectByIds(UserDir.class, Arrays.asList("1", 2, 3));
    }

    @Test
    public void testSave() {
        UserCopy zr = baseService.selectById(UserCopy.class, 1);
        zr.getFtpDirList().forEach(f -> f.setId(null));
//        baseService.updateObject(zr, true);
//        zr = baseService.selectById(UserCopy.class, 1);
//        System.out.println(zr.getFtpDirList().size());

        UserCopy yy = baseService.selectById(UserCopy.class, 2);
        yy.getFtpDirList().addAll(zr.getFtpDirList());
        baseService.update(yy, true);
        yy = baseService.selectById(UserCopy.class, 2);
        System.out.println(yy.getUserDirList().size());
    }

    @Test
    public void testDelete() {
        Collection<UserCopy> userCopies = baseService.selectByIds(UserCopy.class, Arrays.asList(1, 2, 3));
        baseService.batchDelete(Caster.cast(userCopies), true);
    }

    @Test
    public void testLogicDelete() {
        Collection<UserCopy> userCopies = baseService.selectByIds(UserCopy.class, Arrays.asList(1, 2, 3));
        baseService.batchLogicDelete(Caster.cast(userCopies), true);
    }
}
