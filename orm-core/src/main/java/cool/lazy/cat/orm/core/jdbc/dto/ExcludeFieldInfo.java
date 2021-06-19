package cool.lazy.cat.orm.core.jdbc.dto;


import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

import java.util.Objects;

/**
 * @author: mahao
 * @date: 2021/3/20 15:32
 * 忽略属性
 */
public class ExcludeFieldInfo {

    /**
     * 标注该嵌套属性所属的链表结构
     */
    private TableChain chain;
    /**
     * 属性详细信息，如果被忽略的字段是一个映射对象，则此字段为null
     */
    private TableFieldInfo fieldInfo;

    public ExcludeFieldInfo(TableChain chain, TableFieldInfo fieldInfo) {
        this.chain = chain;
        this.fieldInfo = fieldInfo;
    }

    public boolean excludeChain() {
        return null != chain && null == fieldInfo;
    }

    public boolean excludeChainField() {
        return null != chain && null != fieldInfo;
    }

    public boolean excludeField() {
        return null == chain && null != fieldInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExcludeFieldInfo that = (ExcludeFieldInfo) o;

        if (!Objects.equals(chain, that.chain)) {
            return false;
        }
        return Objects.equals(fieldInfo, that.fieldInfo);
    }

    @Override
    public int hashCode() {
        int result = chain != null ? chain.hashCode() : 0;
        result = 31 * result + (fieldInfo != null ? fieldInfo.hashCode() : 0);
        return result;
    }

    public TableChain getChain() {
        return chain;
    }

    public ExcludeFieldInfo setChain(TableChain chain) {
        this.chain = chain;
        return this;
    }

    public TableFieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public ExcludeFieldInfo setFieldInfo(TableFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
        return this;
    }
}
