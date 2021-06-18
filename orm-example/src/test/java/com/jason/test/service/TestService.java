package com.jason.test.service;

import com.jason.test.TestConfiguration;
import com.jason.test.pojo.User;
import com.jason.test.pojo.UserCopy;
import com.lazy.cat.orm.core.base.service.BaseService;
import com.lazy.cat.orm.core.base.util.Caster;
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
    BaseService<Object> baseService;
    @Autowired
    UserService userService;

    @Test
    public void testSelectById() {
        UserCopy userCopy = baseService.selectById(UserCopy.class, 1);
        Collection<UserCopy> userCopies = baseService.selectByIds(UserCopy.class, Arrays.asList(1, 2, 3));
        User user = userService.selectById(1);
        Collection<User> users = userService.selectByIds(Arrays.asList(1, 2, 3));
        System.out.println();
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
