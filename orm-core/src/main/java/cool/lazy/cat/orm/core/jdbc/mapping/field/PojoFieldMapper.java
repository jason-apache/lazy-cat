package cool.lazy.cat.orm.core.jdbc.mapping.field;

import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.Map;
import java.util.Queue;

/**
 * @author: mahao
 * @date: 2021/10/18 11:48
 * 对象结构映射
 */
public interface PojoFieldMapper {

    /**
     * @return 表信息
     */
    TableInfo getTableInfo();

    void setSourceStructure(Map<String, PojoFieldMapper> sourceStructure);

    /**
     * @return 源结构 循环引用的结构
     */
    Map<String, PojoFieldMapper> getSourceStructure();

    /**
     * 根据名称查找
     */
    PojoFieldMapper getMapperByName(String name);

    /**
     * 根据路径循环查找
     */
    PojoFieldMapper getMapperByPath(String path);

    /**
     * 根据路径循环查找
     */
    PojoFieldMapper getMapperByPath(Queue<String> path);

    /**
     * 根据字段名查找
     */
    PojoField getFieldByName(String name);

    /**
     * 根据路径循环查找
     */
    PojoField getFieldByPath(String path);

    /**
     * 根据路径循环查找
     */
    PojoField getFieldByPath(Queue<String> path);

    /**
     * @return 对象字段访问器
     */
    FieldAccessor getFieldAccessor();

    void setFieldAccessor(FieldAccessor fieldAccessorCache);
}
