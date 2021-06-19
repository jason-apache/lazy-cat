package cool.lazy.cat.orm.core.jdbc.dto;


import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.util.TableChainBuildHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/4/13 12:45
 * 级联结构映射，TableChain一旦生成就是固定的
 * 为此，CascadeLevelMapper做为一个补充，可以动态的控制级联等级
 * eg：
 *      public class User {
 *          private Office office;
 *          @ManyToOne(condition = @On(foreignFiled = "department", targetFiled = "id"), cascadeLevel = 2, updatable = false, insertable = false)
 *          public Office getOffice() {
 *              return office;
 *          }
 *      }
 *      对于office的联查层级为2，重新定义联查层级
 *      CascadeLevelMapper.build("office", 5)，则可以在查询中对Office 5层级联查询
 *  一般情况下这个类不希望被用到，在程序运行之前，应当设计好完整的pojo类的关联关系
 *  CascadeLevelMapper只是作为一个补充，在提高级联层级时应考虑性能问题，避免查询多余的对象
 */
public class CascadeLevelMapper {

    private final Map<String, Integer> mapper = new HashMap();

    private CascadeLevelMapper() {
    }

    public static CascadeLevelMapper build(String k, Integer v) {
        return new CascadeLevelMapper().kv(k, v);
    }

    public static CascadeLevelMapper buildAll(Integer v) {
        return new CascadeLevelMapper().kv(TableChainBuildHelper.ALL, v);
    }

    public static CascadeLevelMapper buildEmpty() {
        return new CascadeLevelMapper();
    }

    public CascadeLevelMapper kv(String k, Integer v) {
        mapper.put(k, v);
        return this;
    }

    public boolean isAll() {
        return mapper.containsKey(TableChainBuildHelper.ALL);
    }

    public Integer getLevel(PojoMapping pojoMapping) {
        if (mapper.isEmpty()) {
            return pojoMapping.getCascadeLevel();
        }
        if (isAll()) {
            return mapper.get(TableChainBuildHelper.ALL);
        }
        Integer val = mapper.get(pojoMapping.getFieldInfo().getJavaFieldName());
        if (null != val) {
            return val;
        }
        return pojoMapping.getCascadeLevel();
    }
}
