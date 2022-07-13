package cool.lazy.cat.orm.generator.type;

/**
 * @author : jason.ma
 * @date : 2022/7/11 18:02
 */
public interface TypeCorrespondTable {

    Class<?> javaType(String jdbcType);
}
