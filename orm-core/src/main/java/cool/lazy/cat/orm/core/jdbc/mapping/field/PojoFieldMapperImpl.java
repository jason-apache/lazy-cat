package cool.lazy.cat.orm.core.jdbc.mapping.field;

import cool.lazy.cat.orm.core.jdbc.exception.UnKnowFiledException;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.access.FieldAccessor;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author: mahao
 * @date: 2021/7/30 16:33
 */
public class PojoFieldMapperImpl implements PojoFieldMapper {

    /**
     * pojo字段信息
     */
    private final TableInfo tableInfo;
    private Map<String, PojoFieldMapper> sourceStructure = Collections.emptyMap();
    private FieldAccessor fieldAccessor;

    public PojoFieldMapperImpl(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public TableInfo getTableInfo() {
        return this.tableInfo;
    }

    @Override
    public void setSourceStructure(Map<String, PojoFieldMapper> sourceStructure) {
        this.sourceStructure = sourceStructure;
    }

    @Override
    public Map<String, PojoFieldMapper> getSourceStructure() {
        return sourceStructure;
    }

    @Override
    public PojoFieldMapper getMapperByName(String name) {
        PojoFieldMapper mapper = this.sourceStructure.get(name);
        if (null == mapper) {
            throw new UnKnowFiledException("pojo不存在该字段: " + name);
        }
        return mapper;
    }

    @Override
    public PojoFieldMapper getMapperByPath(String path) {
        PojoFieldMapper mapper = this.getMapperByPath(new LinkedList<>(Arrays.asList(path.split("\\."))));
        if (null == mapper) {
            throw new UnKnowFiledException("pojo不存在该字段: " + path);
        }
        return mapper;
    }

    @Override
    public PojoFieldMapper getMapperByPath(Queue<String> path) {
        String node = path.poll();
        PojoFieldMapper mapper = sourceStructure.get(node);
        if (null == mapper) {
            return null;
        }
        if (path.isEmpty()) {
            return mapper;
        }
        return mapper.getMapperByPath(path);
    }

    @Override
    public PojoField getFieldByName(String name) {
        PojoField fieldInfo = this.tableInfo.getFieldInfoMap().get(name);
        if (null == fieldInfo) {
            throw new UnKnowFiledException("pojo不存在该字段: " + name);
        }
        return fieldInfo;
    }

    @Override
    public PojoField getFieldByPath(String path) {
        PojoField fieldInfo = this.getFieldByPath(new LinkedList<>(Arrays.asList(path.split("\\."))));
        if (null == fieldInfo) {
            throw new UnKnowFiledException("pojo不存在该字段: " + path);
        }
        return fieldInfo;
    }

    @Override
    public PojoField getFieldByPath(Queue<String> path) {
        String node = path.poll();
        PojoFieldMapper mapper = sourceStructure.get(node);
        if (null == mapper) {
            return null;
        }
        if (path.isEmpty()) {
            return mapper.getFieldByPath(node);
        }
        return mapper.getFieldByPath(path);
    }

    @Override
    public FieldAccessor getFieldAccessor() {
        return fieldAccessor;
    }

    @Override
    public void setFieldAccessor(FieldAccessor fieldAccessor) {
        if (null == fieldAccessor) {
            throw new NullPointerException("fieldAccessor 为空");
        }
        this.fieldAccessor = fieldAccessor;
    }

    @Override
    public String toString() {
        return this.tableInfo.getPojoType().getName();
    }
}
