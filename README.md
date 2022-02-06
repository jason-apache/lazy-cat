# 快速开始 :cat:  
    orm-core 底层基于spring jdbc template 在此之上扩展完成了orm  
    orm-api 扩展了orm-core 实现全局自动映射api接口  



## orm-core
   配置: `cool.lazy.cat.orm.core.jdbc.JdbcConfig`  
   你需要先了解这些注解的功能和基本工作方式:  
   `cool.lazy.cat.orm.core.base.annotation.*`  
   **lazy-cat一律以pojo字段的getter方法标注的注解为准 请注意**  
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
    和cool.lazy.cat.orm.core.jdbc.adapter.mapper.DynamicNameMapper  并自定义DataSource、PlatformTransactionManager对象的构建   
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
    @see cool.lazy.cat.orm.core.jdbc.component.CommonComponent  
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
   ####mvn  
    `<dependency>  
        <groupId>cool.lazy-cat</groupId>  
        <artifactId>orm-core-spring-boot-starter</artifactId>  
        <version>2.0.0</version>  
    </dependency>`  

## orm-api
   配置: `cool.lazy.cat.orm.api.ApiConfig`  
   你需要先了解这些注解的功能和基本工作方式:  
   `cool.lazy.cat.orm.api.web.annotation.*`  
   **lazy-cat一律以pojo字段的getter方法标注的注解为准 请注意**
   #### 正确使用方式
    api模块映射了一个根路径作为入口, 又根据Pojo类限定了namespace, 一个完整的api映射路径是由 api映射根路径 + pojo namespace + apiMethod
    事实上 阅读完那些注解, 已经没有什么需要额外注意的地方了. api模块的功能实现比较简单, 在你的pojo类中标注那些注解并访问对应的接口 so, just do it
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
   ####mvn
    `<dependency>  
        <groupId>cool.lazy-cat</groupId>  
        <artifactId>orm-api-spring-boot-starter</artifactId>  
        <version>2.0.0</version>  
    </dependency>`  



##开发者的话
    lazy-cat的第一个快照版仅仅使用了2个月就完成了全部的开发、测试和发布。  
    但随后代码的维护让我苦不堪言，第二个版本的代码删减、重构量基本达到了90%  
    足足花费了我近9个月业余时间去重构、扩展功能。由此可见保持一个良好的编码和设计风格是多么重要的一件事。  
    orm-api的源码2457行代码 orm-core的源码18564行代码  
    orm-api这个项目还是颇有争议的 目前的实现方案可能欠佳  
    个人邮箱: 2628452503@qq.com  

### 一个简单的例子
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    @ApiPojo(nameSpace = "user", entry = {
            @Entry(path = "selectPage", api = QueryPageApiEntry.class, methods = {HttpMethod.POST}),
            @Entry(path = "selectPage", api = QueryPageApiEntry.class, methods = {HttpMethod.GET}),
            @Entry(path = "select", api = QueryApiEntry.class),
            @Entry(path = "save", api = SaveApiEntry.class),
            @Entry(path = "saveForce", api = SaveCascadeApiEntry.class),
            @Entry(path = "delete", api = RemoveApiEntry.class, methods = HttpMethod.POST),
            @Entry(path = "delete", api = RemoveApiEntry.class, methods = {HttpMethod.DELETE, HttpMethod.GET}),
            @Entry(path = "deleteForce", api = RemoveCascadeApiEntry.class),
            @Entry(path = "test", api = TestApiEntry.class)
    })
    @Pojo(trigger = @Trigger(type = RecordPojoTrigger.class), table = @Table(schema = "m1"))
    public class User extends RecordPojo {

        private static final Long serialVersionUID = 2242091901887685062L;
        private String id;
        private String name;
        private Integer age;
        private String education;
        private Sex sex;
        private String phoneNum;
        private String department;
        private Office office;
        private List<UserDir> userDirList;
        private List<FtpDir> ftpDirList;
        private List<UserResource> resourceList;
    
        @Override
        @Column(updatable = false, sort = 1)
        @Id(idGenerator = SequenceIdGenerator.class, parameters = {@Parameter(name = Constant.SEQUENCE_SCHEMA, value = "core"), @Parameter(name = Constant.SEQUENCE_NAME, value = "user_id_seq")})
        @ApiQueryFilter(In.class)
        public String getId() {
            return id;
        }
    
        @Column(sort = 10)
        @ApiQueryFilter(RightLike.class)
        public String getName() {
            return name;
        }
    
        @Column(validator = @Validator(type = CommonValidator.class, notNull = true, parameter = {@Parameter(name = CommonValidator.VALIDATE_INFO_KEY, value = ValidateConstant.VALIDATE_HUMAN_AGE_KEY)}),
                sort = 20)
        @ApiQueryFilter(Equals.class)
        public Integer getAge() {
            return age;
        }
    
        @Column(sort = 30)
        public String getEducation() {
            return education;
        }
    
        @Column(sort = 40, typeConverter = SimpleEnumTypeConverter.class)
        @ApiQueryFilter(Equals.class)
        public Sex getSex() {
            return sex;
        }
    
        @Column(validator = @Validator(type = CommonValidator.class,
                parameter = {@Parameter(name = CommonValidator.VALIDATE_INFO_KEY, value = ValidateConstant.VALIDATE_USER_PHONE_NUM_KEY)}),
                sort = 50)
        public String getPhoneNum() {
            return phoneNum;
        }
    
        @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")},
                cascadeScope = {"userDirList.userFileList.fileContentList"},
                ignoreFields = {"userDirList.userFileList.fileContentList.suffix"},
                cascadeLevel = 3, sort = 10)
        public List<UserDir> getUserDirList() {
            return userDirList;
        }
    
        @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, cascadeLevel = 2, sort = 20)
        public List<FtpDir> getFtpDirList() {
            return ftpDirList;
        }
    
        @Column(sort = 60)
        @ApiQueryFilter(InOrIsNull.class)
        public String getDepartment() {
            return department;
        }
    
        @ManyToOne(condition = @On(foreignFiled = "department", targetFiled = "id"), sort = 30, cascadeScope = {"office.parent"})
        public Office getOffice() {
            return office;
        }
    
        @OneToMany(condition = @On(foreignFiled = "id", targetFiled = "userId"), cascadeLevel = 2, sort = 40)
        public List<UserResource> getResourceList() {
            return resourceList;
        }
    }