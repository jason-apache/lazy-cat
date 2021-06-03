package com.lazy.cat.orm.core.jdbc.mapping;


import com.lazy.cat.orm.core.base.util.CollectionUtil;

import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/11 17:50
 * 记录表与表关联关系
 * eg：
 *      public class User {
 *          private List<UserDir> userDirList;
 *          @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, cascadeLevel = 3, deletable = true)
 *          public List<UserDir> getUserDirList() {
 *              return userDirList;
 *          }
 *      }
 *      在User对应的TableInfo的nestedChainList中，应当生成如下结构的TableChain
 *      nestedChainList: [
 *          {
 *              Class<?> pojoType: com.lazy.kitty...UserDir
 *              String aliasName: ...
 *              chain: [
 *                  {
 *                      Class<?> pojoType: com.lazy.kitty...UserFile
 *                      String aliasName: ...
 *                      chain: [
 *                          {
 *                              Class<?> pojoType: com.lazy.kitty...FileContent
 *                              String aliasName: ...
 *                              chain: null
 *                          }
 *                      ]
 *                  }
 *              ]
 *          }
 *      ]
 *   tableChain在spring启动时生成，将会是一个静态不变的表链，任何关于级联的操作都依靠这个关系对象，比如级联查询、级联修改、新增、删除
 *   需要重新构建级联关系时，应当参照TableChainBuildHelper
 * @see com.lazy.kitty.jdbc.core.util.TableChainBuildHelper#build
 */
public class TableChain {

    /**
     * pojo类型
     */
    private Class<?> pojoType;
    /**
     * 表别名
     */
    private String aliasName;
    private String name;
    /**
     * 映射表的主键
     */
    private TableFieldInfo id;
    /**
     * 对应表附属于pojo的某个字段
     * eg：
     *     public class User {
     *          private List<UserDir> userDirList;
     *          @OneToMany(condition = {@On(foreignFiled = "id", targetFiled = "userId")}, cascadeLevel = 3, deletable = true)
     *          public List<UserDir> getUserDirList() {
     *              return userDirList;
     *          }
     *     }
     *     则belongField为User.userDirList
     */
    private TableFieldInfo belongField;
    /**
     * 位于平铺表链中的位置
     */
    private int flatIndex;
    /**
     * 联查条件，多个条件将采用and处理
     */
    private List<On> joinCondition;
    /**
     * 下一层级
     */
    private List<TableChain> chain;
    /**
     * 上一层级（嵌套表链）
     */
    private TableChain upperChain;
    /**
     * 映射关系
     */
    private PojoMapping pojoMapping;

    public boolean hasChain() {
        return CollectionUtil.isNotEmpty(this.chain);
    }

    public Class<?> getPojoType() {
        return pojoType;
    }

    public TableChain setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        return this;
    }

    public String getAliasName() {
        return aliasName;
    }

    public TableChain setAliasName(String aliasName) {
        this.aliasName = aliasName;
        return this;
    }

    public String getName() {
        return name;
    }

    public TableChain setName(String name) {
        this.name = name;
        return this;
    }

    public TableFieldInfo getId() {
        return id;
    }

    public TableChain setId(TableFieldInfo id) {
        this.id = id;
        return this;
    }

    public TableFieldInfo getBelongField() {
        return belongField;
    }

    public TableChain setBelongField(TableFieldInfo belongField) {
        this.belongField = belongField;
        return this;
    }

    public int getFlatIndex() {
        return flatIndex;
    }

    public TableChain setFlatIndex(int flatIndex) {
        this.flatIndex = flatIndex;
        return this;
    }

    public List<On> getJoinCondition() {
        return joinCondition;
    }

    public TableChain setJoinCondition(List<On> joinCondition) {
        this.joinCondition = joinCondition;
        return this;
    }

    public List<TableChain> getChain() {
        return chain;
    }

    public TableChain setChain(List<TableChain> chain) {
        this.chain = chain;
        return this;
    }

    public TableChain getUpperChain() {
        return upperChain;
    }

    public TableChain setUpperChain(TableChain upperChain) {
        this.upperChain = upperChain;
        return this;
    }

    public PojoMapping getPojoMapping() {
        return pojoMapping;
    }

    public TableChain setPojoMapping(PojoMapping pojoMapping) {
        this.pojoMapping = pojoMapping;
        return this;
    }
}
