# lazy-cat
    一个懒人的无意之举。。。
    core: 
          持久层框架，对spring jdbc template进行再封装，实现orm
          对目前java orm框架均不满意，因此产生了自行封装orm的念头
          dependencies: 
                       org.springframework.boot:spring-boot-starter-jdbc
    
    api: 
         api自动映射，访问指定的api uri，自动转发至业务接口@cool.lazy.cat.orm.api.web.entrust.filter.EntrustFilter
         依赖于core，对数据库基础操作进行抽象，避免重复工作，试图打造出一个快速开发脚手架.
         dependencies: 
                      org.springframework.boot:spring-boot-starter-web
    
    
# 快速开始
    启动类增加 @PojoScan注解，扫描指定包下被@Pojo注解标注的类
    你需要阅读这些注解：
        com.lazy.cat.orm.core.base.annotation.*
        com.lazy.cat.orm.api.web.annotation.*
    并参考example项目中的测试类