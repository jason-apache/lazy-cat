package cool.lazy.cat.orm.core.jdbc.dto;


import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/20 16:31
 * 忽略字段包装类
 */
public class ExcludeFieldInfoWrapper {

    private Set<ExcludeFieldInfo> excludes;
    private Set<String> excludesName;

    public ExcludeFieldInfoWrapper setExcludes(Set<ExcludeFieldInfo> excludes) {
        this.excludes = excludes;
        this.excludesName = excludes.stream().map(e -> e.getFieldInfo() == null ? e.getChain().getBelongField().getJavaFieldName() : e.getFieldInfo().getJavaFieldName()).collect(Collectors.toSet());
        return this;
    }

    /**
     * 是否忽略此字段
     * @param info 字段信息
     * @return 是否忽略此字段
     */
    public boolean isExclude(TableFieldInfo info) {
        for (ExcludeFieldInfo fieldInfo : excludes) {
            if (fieldInfo.excludeField() && fieldInfo.getFieldInfo().equals(info)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否忽略此映射对象
     * @param chain 映射对象的表链节点
     * @return 是否忽略此映射对象
     */
    public boolean isExclude(TableChain chain) {
        for (ExcludeFieldInfo fieldInfo : excludes) {
            if (fieldInfo.excludeChain() && fieldInfo.getChain().equals(chain)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否忽略此映射对象的指定字段
     * @param chain 映射对象的表链节点
     * @param info 字段信息
     * @return 是否忽略此映射对象的指定字段
     */
    public boolean isExclude(TableChain chain, TableFieldInfo info) {
        for (ExcludeFieldInfo fieldInfo : excludes) {
            if (fieldInfo.excludeChainField() && fieldInfo.getChain().equals(chain) && fieldInfo.getFieldInfo().equals(info)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExclude(String filedName) {
        return excludesName.contains(filedName);
    }


    public Set<ExcludeFieldInfo> getExcludes() {
        return excludes;
    }

    public Set<String> getExcludesName() {
        return excludesName;
    }

    public ExcludeFieldInfoWrapper setExcludesName(Set<String> excludesName) {
        this.excludesName = excludesName;
        return this;
    }
}
