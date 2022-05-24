# 简介
lazy-cat是基于spring-jdbcTemplate封装的orm框架  
- orm-parent  父级项目  
- orm-base  基础接口, 常量, 工具类  
- orm-anno  基础注解  
- orm-core  orm映射  
- orm-api-base  api基础代码及注解  
- orm-api   api自动映射  
***
# 快速开始 :cat:  
#### orm-core maven依赖:  
    <dependency>  
        <groupId>cool.lazy-cat</groupId>  
        <artifactId>orm-core-spring-boot-starter</artifactId>  
        <version>2.4.0</version>  
    </dependency>  
启动类增加注解@PojoScan(value = {"xxx.xxx.**"}, excludes = {})  
指定包下标注@Pojo的类将被lazy-cat管理 除此之外可以在配置文件中指定扫描路径和排除路径  
**在配置文件指定时将忽略启动类中的注解**   
一个例子:
```java
@Pojo(table = @Table(schema = "app_core", tableName = "acc_user"))
public class User {
    private String id;
    private String name;
    private Sex sex;
    private Office office;
    private List<UserDir> userDirList;

    @Id(idGenerator = UUIdGenerator.class)
    @Column(updatable = false, sort = 1, name = "acc_user_id")
    public String getId() {
        return id;
    }
    @Column(sort = 10)
    public String getName() {
        return name;
    }

    /**
     * 内置的枚举类型转换器 查询、持久化时自动转换类型.
     */
    @Column(sort = 40, typeConverter = SimpleEnumTypeConverter.class)
    public Sex getSex() {
        return sex;
    }
    @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")},
            cascadeScope = {"userDirList.userFileList.fileContentList"},
            ignoreFields = {"userDirList.userFileList.fileContentList.suffix"},
            sort = 10)
    public List<UserDir> getUserDirList() {
        return userDirList;
    }
    @ManyToOne(condition = @On(foreignFiled = "department", targetFiled = "id"), sort = 30, cascadeLevel = 2)
    public Office getOffice() {
        return office;
    }
}
```
对于数据库表存在的字段应加注@Column注解 默认将字段驼峰转换下划线映射数据库字段  
**以pojo字段的getter方法标注的注解为准 请注意**  
对于关系映射对象, lazy-cat提供三个注解  
- OneToMany  
- ManyToOne  
- OneToOne  

在查询、增删改时根据 **作用域** 自动更新关系映射对象 也可以通过参数来控制是否更新映射对象的值  
所谓 **作用域** 是指注解中配置的cascadeScope或者cascadeLevel参数, 优先取cascadeScope参数  
- cascadeLevel 加载映射对象等级(层级), 如果大于1, 则继续加载映射对象配置的关系映射对象(如果有), 值越大加载层级越深  
- **cascadeScope** 以字符串的形式配置加载映射对象的嵌套对象结构  

**cascadeScope**模式更加灵活控制加载的嵌套对象, 在对象结构复杂的情况下 cascadeLevel可能造成性能问题  
lazy-cat提供了BaseRepository万能类, 可以操作所有被lazy-cat管理的pojo  
```java
@Autowired
BaseRepository baseRepository;
public void test() {
    List<User> ul1 = baseRepository.queryByIds(User.class, Arrays.asList(1, 2, 3));
    User u2 = baseRepository.queryById(User.class, 133);
    // 如果返回了多条数据，将抛出异常
    User w = baseRepository.querySingle(new SearchParamImpl<>(User.class).setCondition(Condition.eq("name", "王五")));
    PageResult<User> upr = baseRepository.queryPage(new SearchParamImpl<>(User.class)
        .setCondition(Condition.eq("office.name", "A公司").and(Condition.lt("age", 35))));
    // 更新或保存 默认更新映射对象 判断对象是否为新记录可重写isNewRecord()方法
    baseRepository.save(ul1);
    // 更新或保存 但不更新映射对象
    baseRepository.save(ul1, false);
    // 删除 默认删除映射对象
    baseRepository.delete(ul1);
    // 删除 担不删除映射对象
    baseRepository.delete(ul1, false);
    // 根据条件删除
    baseRepository.deleteByCondition(User.class, Condition.eq("name", "王五").or(Condition.gte("age", 99)));
    // 根据id删除
    baseRepository.deleteByIds(User.class, Arrays.asList(1, 2, 3));
    // 逻辑删除 默认逻辑删除映射对象 如果类没有逻辑删除字段 则什么也不做
    baseRepository.logicDelete(ul1);
    // 逻辑删除 但不逻辑删除映射对象 如果类没有逻辑删除字段 则什么也不做
    baseRepository.logicDelete(ul1, false);
    // 推断删除 如果类具有逻辑删除字段则执行逻辑删除 否则物理删除 同时更新映射对象
    baseRepository.deleteByInfer(ul1);
    // 同上 但不更新映射对象
    baseRepository.deleteByInfer(ul1, false);
}
```
在查询操作时可以动态指定`作用域` **但增删改操作时只能使用注解声明的作用域**  
```java
List<String> searchScope = Arrays.asList("office.parent.parent.childrenList",
        "userDirList.userFileList.fileContentList.dir", "ftpDirList.ftpFileList.dir");
Condition condition = Condition.in("office.name", Arrays.asList("A公司", "B公司")).and(Condition.eq("sex", "女"));
// 忽略查询的字段
Ignorer ignorer = Ignorer.build("office.level", "sex", "userDirList.userFileList.fileContentList.suffix");
List<User> u = baseRepository.query(new SearchParamImpl<>(User.class).setSearchScope(searchScope).setIgnorer(ignorer).setCondition(condition));
```
事务控制层面提供了泛型接口`BaseService<Object>`  
***

#### orm-api maven依赖  
    <dependency>   
        <groupId>cool.lazy-cat</groupId>  
        <artifactId>orm-api-spring-boot-starter</artifactId>  
        <version>2.4.0</version>  
    </dependency>  
orm-api项目提供了api方法自动映射的功能 依赖了orm-core    
api映射需要一个根入口 默认为lazy-cat 你可以配置ApiConfig的apiPath属性以修改     
一个例子:  
```java
// api请求路径 = api根路径 + '/' + nameSpace + '/' + path
@ApiPojo(nameSpace = "user", entry = {
        @Entry(path = "selectPage", api = QueryPageApiEntry.class, methods = {HttpMethod.POST}),
        @Entry(path = "select", api = QueryApiEntry.class),
        @Entry(path = "save", api = SaveApiEntry.class),
        @Entry(path = "saveForce", api = SaveCascadeApiEntry.class),
        @Entry(path = "delete", api = RemoveApiEntry.class, methods = HttpMethod.POST),
        @Entry(path = "delete", api = RemoveApiEntry.class, methods = {HttpMethod.DELETE, HttpMethod.GET}),
        @Entry(path = "deleteForce", api = RemoveCascadeApiEntry.class)
})
@Pojo(table = @Table(schema = "app_core", tableName = "acc_user"))
public class User {
}
```
此时, 请求http://{server:port}/lazy-cat/user/select 即可触发QueryApiEntry接口  
前提是这个类必须是一个lazy-cat-orm-core扫描管理的类  
你可以使用@ApiQueryFilter注解标注查询条件:  
```java
@Column(sort = 10)
// 查询条件
@ApiQueryFilter(In.class)
public String getName() {
    return name;
}
@Column(validator = @Validator(type = CommonValidator.class, notNull = true, parameter = {@Parameter(name = CommonValidator.VALIDATE_INFO_KEY, value = ValidateConstant.VALIDATE_HUMAN_AGE_KEY)}), sort = 20)
// 查询条件
@ApiQueryFilter(Equals.class)
public Integer getAge() {
    return age;
}
```
http请求参数(QueryInfo.class):  
```json
{
    "pageSize": 50000,
    "startIndex": 0,
    "params": {
        "name": [
            "金克拉",
            "艾莉丝"
        ]
    },
    "orderFields": [
        "createDate"
    ],
    "ignoreFields": [],
    "asc": true
}
```
orm-api提供了7个接口  

| 接口名 | 参数 | 代理的api方法 |  
| :--- | :--- | :--- |  
| QueryApiEntry | QueryInfo.class | commonApiService.select(queryInfo) |  
| QueryPageApiEntry | QueryInfo.class | commonApiService.selectPage(queryInfo) |  
| RemoveApiEntry | `List<Object>` | baseService.deleteByInfer(dataList, false) |  
| RemoveCascadeApiEntry | `List<Object>` | baseService.deleteByInfer(dataList, true) |  
| RemoveByIdsApiEntry | `List<String>` | baseService.deleteByIdsAndInfer(pojoType, ids) |  
| SaveApiEntry | `List<Object>` | baseService.save(dataList, false) |  
| SaveCascadeApiEntry | `List<Object>` | baseService.save(dataList, true) |  

你也可以扩展[自定义的api代理](#扩展自定义api) 自定义请求参数、响应参数、以及真正执行业务逻辑的bean    
除了使用注解配置api请求路径映射之外 orm-api还提供了基于配置文件配置api参数的方式  
```yml
cool:
  lazy-cat:
    servlet:
      # 设置api请求根路径
      api-path: api
      # 声明使用配置文件的方式
      api-pojo-subject-registry-instance: cool.lazy.cat.orm.api.manager.provider.ConfigFileApiPojoSubjectProvider
      # 配置api
      api-entries:
        - pojo-type: com.jason.test.pojo.mysql.User
          name-space: user
          properties:
            - path: select
              api: cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry
              allow-methods: POST,GET
          query-filters:
            # 字段查询条件
            - field: name
              condition: cool.lazy.cat.orm.core.jdbc.sql.condition.type.In
            # 字段查询条件
            - field: age
              condition: cool.lazy.cat.orm.core.jdbc.sql.condition.type.Equals
        - pojo-type: com.jason.test.pojo.mysql.Office
          name-space: officeTest
          properties:
            - path: selectPage
              api: cool.lazy.cat.orm.api.web.entrust.method.QueryApiEntry
              allow-methods: POST,GET
          query-filters:
            # 字段查询条件
            - field: name
              condition: cool.lazy.cat.orm.core.jdbc.sql.condition.type.In
```
***
## orm-core
   配置: `cool.lazy.cat.orm.core.jdbc.JdbcConfig`  
   你需要先了解这些注解的功能和基本工作方式:  
   `cool.lazy.cat.orm.annotation.*`  
   #### 正确使用方式  
    根入口为cool.lazy.cat.orm.core.base.repository.BaseRepository. baseRepository提供了丰富的api供使用  
    在baseRepository之上扩展了service层面:  
        cool.lazy.cat.orm.core.base.service.BaseService、cool.lazy.cat.orm.core.base.service.CommonService  
    service层使用了编程式事务控制 org.springframework.transaction.support.TransactionTemplate   
    如果你不喜欢这样的事务控制方式 可以绕过service层面 直接操作baseRepository并自定义你的事务控制方式  
    其中baseSerive具备类泛型属性 与Pojo类型强绑定. commonService所有方法均为泛型方法, 可作为万能类型的查询方法调用  
    也可以注入Bean BaseService<Object>的形式, 通过泛型强转 也能将BaseService<Object>作为万能类型的持久化方式 
    具体可以参考example项目中test项目的示例  
   #### 扩展多数据源支持
    需要扩展: cool.lazy.cat.orm.core.jdbc.adapter.mapper.JdbcOperationHolderMapper  
    和cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper    
    并自定义DataSource、PlatformTransactionManager对象的构建    
    可参考example项目中 com.jason.test.component.datasource包下的示例  
   #### sql方言扩展
    @see cool.lazy.cat.orm.core.jdbc.sql.dialect.Dialect  
   #### 扩展自定义sql函数
    @see cool.lazy.cat.orm.core.jdbc.sql.dialect.function.FunctionHandler  
   #### 扩展自定义sql条件
    @see cool.lazy.cat.orm.core.jdbc.sql.condition.SqlCondition、   
    cool.lazy.cat.orm.core.jdbc.adapter.mapper.ConditionTypeMapper  
    需要留意cool.lazy.cat.orm.core.jdbc.sql.condition.type包  
    和cool.lazy.cat.orm.core.jdbc.constant.ConditionConstant的CONDITION_TYPE_CACHE属性  
    可以查看example项目中的com.jason.test.component.conditiontype包下的示例  
   #### 扩展组件
    @see cool.lazy.cat.orm.base.component.CommonComponent  
    @see cool.lazy.cat.orm.core.jdbc.component.SpecialColumn  
   #### 基本工作流程
    假设入口为service: 
                    - service 判断当前的操作类型和pojo类型 适配对应的操作数据库源 并得到对应数据库的事务支持 将当前数据库操作信息放入上下文 开启事务 ↴
                    - baseRepository 组装数据... 一系列逻辑判断、赋值 ↴
                    - sqlExcutor 从上下文中获取数据库操作信息 如果没有 则再次适配并放入上下文 ↴
                    - sqlPrinter 打印sql及参数适配 ↴
                    - sqlExcutor 执行扩展组件、sql拦截器、并最终执行sql返回结果 释放上下文信息 ↴
                    - service 由spring控制事务提交或回滚 ↴
                    - finished
***
## orm-api
   配置: `cool.lazy.cat.orm.api.ApiConfig`  
   你需要先了解这些注解的功能和基本工作方式:  
   `cool.lazy.cat.orm.api.base.anno.*`  
   **lazy-cat以pojo字段的getter方法标注的注解为准 请注意**
   #### 正确使用方式
    api模块映射了一个根路径作为入口, 又根据Pojo类限定了namespace, 一个完整的api映射路径是由 api映射根路径 + pojo namespace + apiMethod   
    事实上 阅读完那些注解, 已经没有什么需要额外注意的地方了.   
    api模块的功能实现比较简单, 在你的pojo类中标注那些注解并访问对应的接口 so, just do it  
   #### 扩展自定义api
    @see cool.lazy.cat.orm.api.web.entrust.EntrustApi、cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry  
    EntrustApi代表了直接执行api方法的对象, ApiMethodEntry代表执行某个对象的某个方法  
    可以查看example项目中的实例com.jason.test.api包下的内容  
   ### 注意 如果你扩展了自定义sql条件
    那么你需要留意cool.lazy.cat.orm.core.jdbc.constant.ConditionConstant的CONDITION_TYPE_CACHE属性  
    api模块对查询字段条件的解析实际上是借由工具类实现的cool.lazy.cat.orm.api.util.ConditionHelper  
   #### 基本工作流程
    目前api模块将配置的根路径作为一个@RequestMapping模糊匹配作为总入口 @see cool.lazy.cat.orm.api.web.entrust.BasicEntrustController  
                    - basicEntrustController 经过mvc模糊匹配处理请求 根据当前请求的路径url匹配具体需要执行的api方法 并将当前操作的pojo信息放入上下文 ↴  
                    - apiMethodExecutor 获取对应的ApiMethodEntry 并调用ApiMethodEntry的构建参数方法生成请求参数  
                                        执行一系列的拦截 最终执行真正的方法并返回结果 ↴  
                    - basicEntrustController 交给mvc返回结果 并释放上下文信息 ↴  
                    - finished  
***


   ## 开发者的话
    lazy-cat的第一个快照版仅仅使用了2个月就完成了全部的开发、测试和发布。  
    但随后代码的维护让我苦不堪言，第二个版本的代码删减、重构量基本达到了90%  
    足足花费了我近9个月业余时间去重构、扩展功能。由此可见保持一个良好的编码和设计风格是多么重要的一件事。  
    orm-api的源码2457行代码 orm-core的源码18564行代码  
    orm-api这个项目还是颇有争议的 目前的实现方案可能欠佳  
    个人邮箱: 2628452503@qq.com  
