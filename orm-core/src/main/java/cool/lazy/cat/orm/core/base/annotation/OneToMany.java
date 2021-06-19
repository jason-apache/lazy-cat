package cool.lazy.cat.orm.core.base.annotation;

import cool.lazy.cat.orm.core.base.service.BaseService;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mahao
 * @date: 2021/3/11 13:05
 * 定义对象一对多映射关系，用来标注引用对象类型，不能和Column注解一同使用
 * eg：
 *      private List<UserDir> userDirList;
 *      @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, deletable = true)
 *      public List<UserDir> getUserDirList() {
 *          return userDirList;
 *      }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OneToMany {

    /**
     * 关联条件，多个条件将以and处理
     */
    On[] condition();

    /**
     * 查询层级
     * eg：
     *      public class User {
     *          private List<UserDir> userDirList;
     *          @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")})
     *          public List<UserDir> getUserDirList() {
     *               return userDirList;
     *          }
     *      }
     *      其中，UserDir定义了一个OneToMany的UserFileList
     *      UserFile中定义了一个OneToMany的fileContentList 和一个ManyToOne的UserDir
     *      cascadeLevel = 1 ： ==> 查询User
     *                                      ==关联查询==> UserDir
     *      cascadeLevel = 2 ： ==> 查询User
     *                                      ==关联查询==> UserDir
     *                                                           ==关联查询==> UserFile
     *      cascadeLevel = 3 ： ==> 查询User
     *                                      ==关联查询==> UserDir
     *                                                           ==关联查询==> UserFile
     *                                                                                 ==关联查询==> UserDir
     *                                                                                 ==关联查询==> FileContent
     */
    int cascadeLevel() default 1;

    /**
     * 映射对象是否参与新增
     * @see BaseService#save
     * @see BaseService#insert
     * eg：
     *      user携带一个userDirList调用save或者insert方法，并指定cascade为true
     *      执行userDirList中id不为空的对象修改操作，id为空的对象新增操作
     *      并将对象关联关系条件赋值
     *          @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")})
     *      将从user对象中取值"id" 赋值给UserDirList中的每一个元素的 "userId"字段
     *      继续向下传播，取值于cascadeLevel，继续尝试从userDirList的关联映射中取insertable = true的对象进行操作
     */
    boolean insertable() default true;

    /**
     * 映射对象是否参与修改
     * 逻辑同上，将根据关联条件进行修改
     */
    boolean updatable() default true;

    /**
     * 映射对象是否参与删除
     * 逻辑同上，将根据关联条件进行删除
     */
    boolean deletable() default false;
}
