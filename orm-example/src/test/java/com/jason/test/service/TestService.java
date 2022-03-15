package com.jason.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.test.TestConfiguration;
import com.jason.test.constant.Sex;
import com.jason.test.pojo.mysql.FtpDir;
import com.jason.test.pojo.mysql.FtpFile;
import com.jason.test.pojo.mysql.Office;
import com.jason.test.pojo.mysql.User;
import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.service.CommonService;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.core.jdbc.Ignorer;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import cool.lazy.cat.orm.core.jdbc.sql.condition.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/4/14 21:06
 */
@SpringBootTest(classes = TestConfiguration.class)
public class TestService {

    public static final List<User> insertDataList = new ArrayList<>(1000);

    static {
        try {
            String insertJsonStr = "[{\"name\":\"金克拉\",\"age\":20,\"education\":\"大学本科\",\"sex\":\"WOMAN\",\"phoneNum\":\"13169341038\",\"department\":\"3\",\"userDirList\":[{\"name\":\"学习资料\",\"userFileList\":[{\"fileName\":\"a接w接外圈刮\",\"fileContentList\":[{\"content\":\"a接w\",\"suffix\":\"txt\"},{\"content\":\"接外圈刮\",\"suffix\":\"txt\"}]},{\"fileName\":\"后端的自我修养\",\"fileContentList\":[{\"content\":\"后端的自我修养\",\"suffix\":\"txt\"}]}]},{\"name\":\"java从入门到入土\",\"userFileList\":[{\"fileName\":\"Java编程思想\",\"fileContentList\":[{\"content\":\"java\",\"suffix\":\"txt\"},{\"content\":\"编程思想\",\"suffix\":\"txt\"}]}]},{\"name\":\"other\",\"userFileList\":[{\"fileName\":\"完美世界\",\"fileContentList\":null}]}],\"ftpDirList\":[{\"name\":\"/data/user\",\"ftpFileList\":[{\"name\":\"如何让富婆爱上我.txt\"},{\"name\":\"富婆联系方式.txt\"},{\"name\":\"select * from word where some one like me /r/n ## nothing found\"}]},{\"name\":\"/\"},{\"name\":\"/data\"}]},{\"name\":\"艾莉丝\",\"age\":29,\"education\":\"大学本科\",\"sex\":\"WOMAN\",\"phoneNum\":\"13169341038\",\"department\":\"3\",\"userDirList\":[{\"name\":\"学习资料\",\"userFileList\":[{\"fileName\":\"a接w接外圈刮\",\"fileContentList\":[{\"content\":\"a接w\",\"suffix\":\"txt\"},{\"content\":\"接外圈刮\",\"suffix\":\"txt\"}]},{\"fileName\":\"后端的自我修养\",\"fileContentList\":[{\"content\":\"后端的自我修养\",\"suffix\":\"txt\"}]}]},{\"name\":\"java从入门到入土\",\"userFileList\":[{\"fileName\":\"Java编程思想\",\"fileContentList\":[{\"content\":\"java\",\"suffix\":\"txt\"},{\"content\":\"编程思想\",\"suffix\":\"txt\"}]}]}],\"ftpDirList\":[{\"name\":\"/data/user\",\"ftpFileList\":[{\"name\":\"如何让富婆爱上我.txt\"},{\"name\":\"富婆联系方式.txt\"},{\"name\":\"select * from word where some one like me /r/n ## nothing found\"}]},{\"name\":\"/\"},{\"name\":\"/data\"}]}]";
            insertDataList.addAll(new ObjectMapper().readValue(insertJsonStr, new TypeReference<List<User>>() {}));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class SelectApiTester {

        @Autowired
        CommonService commonService;
        @Autowired
        UserService userBaseService;
        @Autowired
        BaseService<Object> baseService;

        @Test
        public void testSelect() {
            List<User> ul1 = userBaseService.select(userBaseService.buildSearchParam());
            PageResult<User> up1 = userBaseService.selectPage(userBaseService.buildSearchParam().setIndex(2000).setPageSize(700));
            User u1 = userBaseService.selectById(911);
            List<User> ul2 = userBaseService.selectByIds(Arrays.asList(10001, 15001, 20001));
            SearchParam<User> userSearchParam = userBaseService.buildSearchParam();
            userSearchParam.setSearchScope(Arrays.asList("office.parent.parent.childrenList", "office.parent.childrenList"));
            List<User> ul3 = userBaseService.select(userSearchParam.setCondition(Condition.like("office.name", "开发")
                    .and(Condition.eq("office.parent.level", 2)).and(userSearchParam.getCondition())));
            SearchParam<Office> officeSearchParam = commonService.buildSearchParam(Office.class);
            List<Office> upperOffices = commonService.select(officeSearchParam.setCondition(Condition.eq("level", 1).and(officeSearchParam.getCondition())));
            userSearchParam = userBaseService.buildSearchParam();
            userSearchParam.setSearchScope(Arrays.asList("office.parent.parent.childrenList",
                    "userDirList.userFileList.fileContentList.dir", "ftpDirList.ftpFileList.dir"));
            userSearchParam.setIgnorer(Ignorer.build("office.parent.parent.childrenList.level"));
            PageResult<User> up2 = commonService.selectPage(userSearchParam.setPageSize(500));
            System.out.println();
        }

        @Test
        public void testSelectByComplexCondition() {
            // 年龄在18 - 25 之间的女性
            Condition c1 = Condition.eq("sex", Sex.WOMAN.name()).and(Condition.lte("age", 25)).and(Condition.gte("age", 18));
            // 年龄小于35的女开发
            Condition c2 = Condition.like("office.name", "开发").and(Condition.eq("sex", Sex.WOMAN.name())).and(Condition.lte("age", 35));
            // 年龄小于等于25的女性或研究生 或者年龄小于等于30的女本科生
            Condition c3 = Condition.eq("sex", Sex.WOMAN.name()).and(Condition.lte("age", 25)
                    .or(Condition.eq("education", "硕士研究生"))
                    .or(Condition.eq("education", "大学本科").and(Condition.lte("age", 30))));
            c1.or(c2).or(c3);
            PageResult<User> userPageResult = userBaseService.selectPage(userBaseService.buildSearchParam().addCondition(c1)
                    .setOrderBy(OrderBy.buildOrderBy(false, "age", "name")).setPageSize(10000));
            System.out.println();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class SaveApiTester {

        @Autowired
        BaseService<User> userBaseService;
        @Autowired
        CommonService commonService;
        @Autowired
        BaseService<Object> baseService;

        @Test
        public void testSave() {
            for (User user : TestService.insertDataList) {
                userBaseService.save(user, true);
            }
        }

        @Test
        public void testBatchSave() {
            userBaseService.save(TestService.insertDataList, true);
        }

        @Test
        public void testSave2() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class);
            userSearchParam.setCondition(Condition.in("name", Arrays.asList("艾莉丝", "金克拉")).and(userSearchParam.getCondition()));
            List<User> userList = commonService.select(userSearchParam);
            Office rootOrg = commonService.selectSingleObj(commonService.buildSearchParam(Office.class).setCondition(Condition.eq("level", 1)));
            Office newOffice = new Office().setParent(rootOrg).setName("新研发部").setCode("0015000");
            // cascade置为true 则会自动更新newOffice的parentId字段 但由于Office的parent属性没有开放 增|删|改 权限 故不会修改newOffice对象的parent对象
            baseService.save(newOffice, true);
            // 给每一个user对象重新赋值office对象 cascade置为true 会自动更新user的department字段 但由于User的office属性没有开放 增|删|改 权限 故不会修改user对象的office对象
            userList.forEach(u -> u.setOffice(newOffice));
            baseService.save(Caster.cast(userList), true);
            userList = commonService.select(userSearchParam);
            System.out.println();
        }

        @Test
        public void testUpdate() {
            List<User> userList = commonService.select(commonService.buildSearchParam(User.class));
            List<Office> officeList = commonService.select(commonService.buildSearchParam(Office.class).setCondition(Condition.eq("level", 3)));
            int seed = officeList.size();
            Random random = new Random();
            for (User user : userList) {
                user.setDepartment(officeList.get(random.nextInt(seed)).getId());
            }
            baseService.save(Caster.cast(userList), false);
        }

        @Test
        public void testSaveOffice() {
            SearchParam<Office> officeSearchParam = commonService.buildSearchParam(Office.class);
            Office root = commonService.selectSingleObj(officeSearchParam.setCondition(Condition.eq("level", 1).and(officeSearchParam.getCondition())));
            root.getChildrenList().add(new Office().setCode("new year"));
            baseService.save(root, true);
            Office o = new Office().setCode("tomorrow will be fine");
            o.setParent(root);
            baseService.save(o, true);
            o = commonService.selectSingleObj(commonService.buildSearchParam(Office.class).setCondition(Condition.eq("code", o.getCode())));
            root = commonService.selectSingleObj(officeSearchParam.setCondition(Condition.eq("level", 1).and(officeSearchParam.getCondition())));
            System.out.println();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class UpdateApiTester {

        @Autowired
        UserService userService;
        @Autowired
        CommonService commonService;
        @Autowired
        OfficeService officeService;
        @Autowired
        BaseService<Object> baseService;

        @Test
        public void testUpdateJinkra() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class);
            userSearchParam.setCondition(Condition.eq("name", "金克拉").and(userSearchParam.getCondition()));
            User jinkra = commonService.selectSingleObj(userSearchParam);
            // 增加一个ftp文件夹
            jinkra.getFtpDirList().add(new FtpDir().setName("一个新增的dir" + System.currentTimeMillis())
                    .setFtpFileList(Collections.singletonList(new FtpFile().setName("一个新增的文件" + System.currentTimeMillis()))));
            userService.update(jinkra, true);
            jinkra = commonService.selectSingleObj(userSearchParam);
            System.out.println();
        }

        @Test
        public void testBatchUpdate() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class);
            userSearchParam.setCondition(Condition.eq("sex", Sex.WOMAN.name()).and(userSearchParam.getCondition()));
            List<User> userList = commonService.select(userSearchParam);
            for (User user : userList) {
                user.setEducation("硕士研究生");
            }
            userService.update(userList, true);
        }

        @Test
        public void testBatchUpdateIgnoreNull() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class);
            userSearchParam.setCondition(Condition.eq("sex", Sex.WOMAN.name()).and(userSearchParam.getCondition())).setPageSize(200);
            Collection<User> userList = commonService.selectPage(userSearchParam).getPageContent();
            for (User user : userList) {
                user.setEducation("硕士研究生");
                user.setAge(null);
                user.setName(null);
            }
            userService.update(userList, true, true);
        }

        @Test
        public void testUpdateIgnoreNull() {
            SearchParam<Office> officeSearchParam = commonService.buildSearchParam(Office.class);
            Office mainOrg = commonService.selectSingleObj(officeSearchParam.setCondition(Condition.eq("code", "001")));
            for (Office office : mainOrg.getChildrenList()) {
                office.setName(null);
            }
            baseService.update(mainOrg, true, true);
            mainOrg = commonService.selectSingleObj(officeSearchParam);
            System.out.println();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    static final class DeleteApiTester {

        @Autowired
        BaseService<Object> baseService;
        @Autowired
        CommonService commonService;

        @Test
        public void testDelete() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class)
                    .setCondition(Condition.in("name", Arrays.asList("邹肉", "谭吏机")));
            List<User> userList = commonService.select(userSearchParam);
            for (User user : userList) {
                baseService.delete(user, true);
            }
            userSearchParam.setCondition(Condition.riLike("name", "毛"));
            userList = commonService.select(userSearchParam);
            baseService.delete(Caster.cast(userList), true);
            userSearchParam.setCondition(Condition.riLike("name", "郑"));
            userList = commonService.select(userSearchParam);
            baseService.deleteByIds(User.class, userList.stream().map(User::getId).collect(Collectors.toList()));
        }

        @Test
        public void testLogicDelete() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class);
            userSearchParam.setCondition(Condition.in("name", Arrays.asList("邹肉", "谭吏机", "阎尤"))
                    .and(Condition.eq("sex", Sex.WOMAN.name())).and(userSearchParam.getCondition()));
            List<User> userList = commonService.select(userSearchParam);
            for (User user : userList) {
                baseService.logicDelete(user, true);
            }
            userSearchParam.setCondition(Condition.riLike("name", "阎"));
            userList = commonService.select(userSearchParam);
            baseService.logicDelete(Caster.cast(userList), true);
            userSearchParam.setCondition(Condition.riLike("name", "郑"));
            userList = commonService.select(userSearchParam);
            baseService.logicDeleteByIds(User.class, userList.stream().map(User::getId).collect(Collectors.toList()));
            System.out.println();
        }

        @Test
        public void testDeleteByInfer() {
            SearchParam<User> userSearchParam = commonService.buildSearchParam(User.class);
            userSearchParam.setCondition(Condition.in("name", Arrays.asList("金克拉", "艾莉丝"))
                    .and(Condition.eq("sex", Sex.WOMAN.name())).and(userSearchParam.getCondition()));
            List<User> userList = commonService.select(userSearchParam);
            System.out.println();
            for (User user : userList) {
                baseService.deleteByInfer(user, true);
            }
            userSearchParam.setCondition(Condition.in("name", Arrays.asList("邹肉", "谭吏机")));
            userList = commonService.select(userSearchParam);
            baseService.deleteByInfer(Caster.cast(userList), false);
            for (User user : userList) {
                if (null != user.getFtpDirList()) {
                    baseService.deleteByInfer(Caster.cast(user.getFtpDirList()), true);
                }
                if (null != user.getUserDirList()) {
                    baseService.deleteByInfer(Caster.cast(user.getUserDirList()), true);
                }
            }
            userSearchParam.setCondition(Condition.riLike("name", "郑"));
            userList = commonService.select(userSearchParam);
            baseService.deleteByIdsAndInfer(User.class, userList.stream().map(User::getId).collect(Collectors.toList()));
        }
    }

}
