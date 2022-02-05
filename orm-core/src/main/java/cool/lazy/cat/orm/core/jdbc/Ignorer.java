package cool.lazy.cat.orm.core.jdbc;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/4/13 19:46
 * 协助查询时需要排除的字段的工具
 */
public final class Ignorer {

    /**
     * 排除模式
     * EXCLUDE（排除fields中的字段）
     * INCLUDE（排除所有不在fields中的字段）
     */
    private final IgnoreModel ignoreModel;
    /**
     * 字段名称（pojo中不存在该字段时，可能引发异常）
     * 可以是一个嵌套的属性，也可以是一个映射对象
     * eg：
     *      public class xxx {
     *          private String size;
     *          private Office office;
     *          private User user;
     *      }
     *      Ignorer.build("size", "office.name", "office.level", "user")
     *      将排除查询xxx的size属性，office对象的name、level属性 以及忽略查询整个user对象
     */
    private final Set<String> fields;

    private Ignorer(IgnoreModel ignoreModel, Set<String> fields) {
        this.ignoreModel = ignoreModel;
        this.fields = fields;
    }

    public static Ignorer build(String ...fields) {
        return build(IgnoreModel.EXCLUDE, fields);
    }

    public static Ignorer build(IgnoreModel ignoreModel, String ...fields) {
        if (null == ignoreModel) {
            ignoreModel = IgnoreModel.EXCLUDE;
        }
        if (null == fields) {
            return null;
        }
        return build(ignoreModel, new HashSet<>(Arrays.asList(fields)));
    }

    public static Ignorer build(Set<String> fields) {
        return build(IgnoreModel.EXCLUDE, fields);
    }

    public static Ignorer build(IgnoreModel ignoreModel, Set<String> fields) {
        if (null == ignoreModel) {
            ignoreModel = IgnoreModel.EXCLUDE;
        }
        if (null == fields) {
            return null;
        }
        return new Ignorer(ignoreModel, fields);
    }

    public Set<String> getFields() {
        return fields;
    }

    public IgnoreModel getIgnoreModel() {
        return ignoreModel;
    }
}
