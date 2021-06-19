package cool.lazy.cat.orm.core.base.util;


import cool.lazy.cat.orm.core.jdbc.IgnoreModel;

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
     * 应当注意的是，执行update操作时
     */
    private final String[] fields;

    private static final String[] EMPTY_FIELDS = new String[0];

    public static final Ignorer EMPTY_IGNORE = build(EMPTY_FIELDS);

    private Ignorer(IgnoreModel ignoreModel, String[] fields) {
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
        return new Ignorer(ignoreModel, fields);
    }

    public static String[] getFields(Ignorer ignorer) {
        if (null == ignorer) {
            return EMPTY_FIELDS;
        }
        return ignorer.fields;
    }

    public IgnoreModel getIgnoreModel() {
        return ignoreModel;
    }
}
