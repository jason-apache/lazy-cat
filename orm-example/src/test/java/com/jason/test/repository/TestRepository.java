package com.jason.test.repository;

import com.jason.test.TestConfiguration;
import com.jason.test.pojo.Office;
import com.jason.test.pojo.User;
import com.jason.test.pojo.UserCopy;
import com.jason.test.pojo.UserDir;
import cool.lazy.cat.orm.core.base.bo.PageResult;
import cool.lazy.cat.orm.core.base.bo.QueryInfo;
import cool.lazy.cat.orm.core.base.repository.BaseRepository;
import cool.lazy.cat.orm.core.base.service.BaseService;
import cool.lazy.cat.orm.core.base.util.Caster;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.context.FullAutoMappingContext;
import cool.lazy.cat.orm.core.jdbc.IgnoreModel;
import cool.lazy.cat.orm.core.jdbc.OrderBy;
import cool.lazy.cat.orm.core.jdbc.analyzer.ConditionAnalyzer;
import cool.lazy.cat.orm.core.jdbc.analyzer.FieldInfoCatcher;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.dto.CascadeLevelMapper;
import cool.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import cool.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.param.SimpleUpdateParam;
import cool.lazy.cat.orm.core.jdbc.provider.SqlParamProvider;
import cool.lazy.cat.orm.core.jdbc.util.TableChainBuildHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/7 17:25
 */
@SpringBootTest(classes = TestConfiguration.class)
public class TestRepository {

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    protected FieldInfoCatcher fieldInfoCatcher;
    @Autowired
    protected ConditionAnalyzer conditionAnalyzer;
    @Autowired
    protected BaseRepository<Object> baseRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected SqlParamProvider sqlParamProvider;
    @Autowired
    protected PojoTableManager pojoTableManager;
    @Autowired
    protected BaseService<User> baseService;

    @Test
    public void testQueryPage() {
        // 一对多查询时忽略多的一方的查询，否则分页将失效
        PageResult<User> userPageResult = baseRepository.queryPage(baseService.buildSearchParam(User.class)
                .setPageSize(150).setIgnorer(Ignorer.build("userDirList", "ftpDirList")));
        PageResult<UserDir> userDirPageResult = baseRepository.queryPage(baseService.buildSearchParam(UserDir.class).setPageSize(5));
    }

    @Test
    public void testQuery() {
        List<User> userList = baseRepository.query(baseService.buildSearchParam(User.class));
        List<UserDir> userDirs = baseRepository.query(baseService.buildSearchParam(UserDir.class));
    }

    @Test
    public void testSelectByInfo() {
        FullAutoMappingContext.setPojoType(User.class);
        List<Object> objects = baseRepository.selectByInfo(new QueryInfo());
        FullAutoMappingContext.setPojoType(UserDir.class);
        List<Object> objects1 = baseRepository.selectByInfo(new QueryInfo());
    }

    @Test
    public void testSelectSingle1() {
        User user = baseRepository.selectSingle(User.class, Condition.eq("id", 1));
    }

    @Test
    public void testSelect() {
        List<User> userList = baseRepository.select(User.class, Condition.eq("sex", "女"));
        List<UserDir> userDirs = baseRepository.select(UserDir.class, Condition.notNull("id"));
    }

    @Test
    public void testSelectPage() {
        FullAutoMappingContext.setPojoType(User.class);
        PageResult<Object> objectPageResult = baseRepository.selectPage(new QueryInfo().setPageSize(150)
                .setIgnoreFields(new String[]{"userDirList", "ftpDirList"}));
        FullAutoMappingContext.setPojoType(UserDir.class);
        PageResult<Object> objectPageResult1 = baseRepository.selectPage(new QueryInfo());
        // 一对多查询时忽略多的一方的查询，否则分页将失效
        PageResult<User> userPageResult = baseRepository.selectPage(User.class, Condition.EMPTY_CONDITION,
                0, 150, Ignorer.build("userDirList", "ftpDirList"));
        PageResult<UserDir> userDirPageResult = baseRepository.selectPage(UserDir.class, Condition.EMPTY_CONDITION, 0, 5, Ignorer.build());
    }

    @Test
    public void testGetFiledInfo() {
        TableFieldInfo byName = fieldInfoCatcher.getByName(User.class, "userDirList.userFileList.fileName", false);
        System.out.println(byName);
    }

    @Test
    public void testCondition() {
        // 1. 30岁以下开发部门女 或 (开发部门的女研究生 或 20岁以下开发部门女本科生)
        Condition c1 = Condition.lt("age", 30).and(Condition.eq("sex", "女")).and(Condition.eq("office.name", "A_开发部门"));
        Condition c2 = Condition.eq("office.name", "A_开发部门").and(Condition.eq("sex", "女")).and(Condition.eq("education", "硕士研究生"));
        c2.or(Condition.lte("age", 20).and(Condition.eq("education", "大学本科")).and(Condition.eq("sex", "女"))
                .and(Condition.eq("office.name", "B_开发部门")).and(Condition.in("userDirList.userFileList.fileName", Arrays.asList("死神", "vs", "火影"))));
        c1.or(c2);
        List<User> select = baseRepository.select(User.class, c1, OrderBy.buildOrderBy(false, "name"), Ignorer.build("age"));
        System.out.println();
        List<Office> office = baseRepository.select(Office.class, null, OrderBy.buildOrderBy(false, "name"));
        System.out.println(office);
        PageResult<User> pageResult = baseRepository.selectPage(User.class, c1, 0 , 700, OrderBy.buildOrderBy(false, "name"));
        System.out.println();
        PageResult<Office> level = baseRepository.selectPage(Office.class, Condition.gt("level", 0), 0, 20, OrderBy.buildOrderBy(false, "name"));
        System.out.println(level);
    }

    @Test
    public void testDml() {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "J");
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update("insert into user(name) values (:name)", new MapSqlParameterSource(param), keyHolder);
        System.out.println(update);
        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        Map<String, Object> keys = keyHolder.getKeys();
        System.out.println(keyHolder.getKey().intValue());
    }

    @Test
    public void testBatchDml() {
        int dataCount = 10;
        Map<String, Object>[] maps = new HashMap[dataCount];
        for (int i = 0; i < dataCount; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", "J" + i);
            maps[i] = data;
        }
        int[] ints = jdbcTemplate.batchUpdate("insert into user(name) values (:name)", maps);
        System.out.println(ints);
    }

    @Test
    public void testGeneratorDmlSql() {
        PageResult<User> pageResult = baseRepository.selectPage(User.class, null, 0, 50000, Ignorer.build("userDirList", "ftpDirList"));
        List<UserCopy> copyList = pageResult.getPageContent().stream().map(u -> {
            UserCopy copy = new UserCopy();
            BeanUtils.copyProperties(u, copy);
            return copy;
        }).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        SqlParamHolder holder = sqlParamProvider.getInsertSql(new SimpleUpdateParam().setData(copyList));
        int[] ints = jdbcTemplate.batchUpdate(holder.getSql(), holder.getParamSources());
        System.out.println("done: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void testValidator() {
        UserCopy u1 = new UserCopy();
        u1.setSex("女").setPhoneNum("18224528257").setName("aa");
        UserCopy u2 = new UserCopy();
        u2.setSex("女").setPhoneNum("18224528257").setName("bb");
    }

    @Test
    public void testInsert() {
        List<User> userAll = baseRepository.select(User.class, null, OrderBy.buildOrderBy(true, "id"));
        List<UserCopy> copyList = new ArrayList<>(userAll.size());
        userAll.forEach(u -> {
            UserCopy uc = new UserCopy();
            BeanUtils.copyProperties(u, uc);
            copyList.add(uc);
        });
        int i = baseRepository.batchInsert(Caster.cast(copyList));
        List<UserCopy> copyAll = baseRepository.select(UserCopy.class, null, OrderBy.buildOrderBy(true, "name"));
        System.out.println(copyAll.size());
    }

    @Test
    public void testDelete() {
        int deleted = baseRepository.delete(UserCopy.class, Condition.notNull("id"));
        System.out.println(deleted);
    }

    @Test
    public void testUpdate() {
        UserCopy single = baseRepository.selectSingle(UserCopy.class, Condition.eq("name", "邹肉").and(Condition.eq("sex", "女")));
        baseRepository.update(single, Condition.eq("id", single.getId()), true);
        System.out.println();
    }

    @Test
    public void testSelectSingle() {
        UserCopy single = baseRepository.selectSingle(UserCopy.class, Condition.eq("name", "邹肉").and(Condition.eq("sex", "女")));
        System.out.println();
    }

    @Test
    public void testBuildChain() {
        TableInfo tableInfo = pojoTableManager.getByPojoType(Office.class).getTableInfo();
        List<PojoMapping> mappings = CollectionUtil.concat(tableInfo.getOneToOneMapping(), tableInfo.getOneToManyMapping(), tableInfo.getManyToOneMapping());
        TableChainHolder holder = TableChainBuildHelper.build(tableInfo, mappings, pojoTableManager.getAllSubjectMap(), CascadeLevelMapper.buildAll(5));
        System.out.println();
    }

    @Test
    public void testSelectByStructure() {
        List<Office> structure = baseRepository.selectByStructure(Office.class, CascadeLevelMapper.build("parent", 5).kv("childrenList", 3),
                null, OrderBy.buildOrderBy(false, "name"), Ignorer.build("parent.childrenList", "parent.parent.childrenList"));
        System.out.println();
    }

    @Test
    public void testIncludeModel() {
        Ignorer ignorer = Ignorer.build(IgnoreModel.INCLUDE, "userDirList", "userDirList.userFileList", "userDirList.userFileList.fileContentList", "ftpDirList", "ftpDirList.ftpFileList");
        List<UserCopy> userCopyList = baseRepository.selectByStructure(UserCopy.class, CascadeLevelMapper.build("userDirList", 4).kv("ftpDirList", 2),
                null, null, ignorer);
        System.out.println();
    }
}
