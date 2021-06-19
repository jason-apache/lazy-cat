package cool.lazy.cat.orm.core.jdbc.analyzer;

import cool.lazy.cat.orm.core.base.util.BeanFieldUtil;
import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.IgnoreModel;
import cool.lazy.cat.orm.core.jdbc.dto.ExcludeFieldInfo;
import cool.lazy.cat.orm.core.jdbc.dto.ExcludeFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.dto.TableFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.exception.UnKnowFiledException;
import cool.lazy.cat.orm.core.jdbc.manager.PojoTableManager;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/16 13:56
 */
public class DefaultFieldInfoCatcher implements FieldInfoCatcher {

    @Autowired
    protected PojoTableManager pojoTableManager;

    @Override
    public TableFieldInfo getByName(Class<?> pojoType, String fieldName, boolean strictModel) {
        return this.getByName(pojoTableManager.getByPojoType(pojoType).getTableInfo(), fieldName, strictModel);
    }

    @Override
    public TableFieldInfo getByName(TableInfo tableInfo, String fieldName, boolean strictModel) {
        List<TableFieldInfo> fieldInfoList = tableInfo.getFieldInfoList();
        TableFieldInfo fieldInfo = fieldInfoList.stream().filter(f -> f.getJavaFieldName().equals(fieldName)).findFirst().orElse(null);
        if (null == fieldInfo && strictModel) {
            throw new UnKnowFiledException("pojo不存在该字段：" + fieldName);
        }
        return fieldInfo;
    }

    @Override
    public TableFieldInfoWrapper getNestedFiledByName(List<TableChain> nestedChainList, String fieldName, boolean strictModel) {
        return this.getFieldByPath(nestedChainList, fieldName, strictModel);
    }

    /**
     * 递归查找嵌套的属性
     * @param nestedChainList 嵌套的表链
     * @param fieldName 属性名
     * @param strictModel 严格模式
     * @return 嵌套的属性
     */
    private TableFieldInfoWrapper getFieldByPath(List<TableChain> nestedChainList, String fieldName, boolean strictModel) {
        int i = fieldName.indexOf(".");
        String curNode = i == -1 ? fieldName : fieldName.substring(0, i);
        String nextNode = i == -1 ? null : fieldName.substring(i + 1);
        for (TableChain chain : nestedChainList) {
            PojoMapping pojoMapping = chain.getPojoMapping();
            if (pojoMapping.getFieldInfo().getJavaFieldName().equals(curNode)) {
                if (StringUtil.isNotBlank(nextNode)) {
                    if (chain.hasChain()) {
                        return this.getFieldByPath(chain.getChain(), nextNode, strictModel);
                    } else {
                        TableFieldInfo fieldInfo = pojoTableManager.getByPojoType(chain.getPojoType()).getTableInfo().getFieldInfoList()
                                .stream().filter(f -> f.getJavaFieldName().equals(nextNode)).findFirst().orElse(null);
                        if (null == fieldInfo && strictModel) {
                            throw new UnKnowFiledException("pojo不存在该字段：" + fieldName);
                        }
                        return new TableFieldInfoWrapper(chain, fieldInfo);
                    }
                }
            } else if(StringUtil.isBlank(nextNode)) {
                Class<?> pojoType = chain.getUpperChain() == null ? chain.getPojoType() : chain.getUpperChain().getPojoType();
                TableFieldInfo fieldInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo().getFieldInfoList()
                        .stream().filter(f -> f.getJavaFieldName().equals(curNode)).findFirst().orElse(null);
                if (null == fieldInfo) {
                    return null;
                }
                return new TableFieldInfoWrapper(chain.getUpperChain(), fieldInfo);
            }
        }
        return null;
    }

    @Override
    public ExcludeFieldInfoWrapper filterExclude(Class<?> pojoType, List<TableChain> nestedChainList, Ignorer ignorer) {
        String[] ignoreFields = Ignorer.getFields(ignorer);
        if (CollectionUtil.isEmpty(ignoreFields)) {
            if (null != ignorer) {
                if (IgnoreModel.INCLUDE == ignorer.getIgnoreModel()) {
                    Set<ExcludeFieldInfo> result = new HashSet<>();
                    this.include(null, pojoTableManager.getByPojoType(pojoType).getTableInfo(), new String[0], nestedChainList, result);
                    return new ExcludeFieldInfoWrapper().setExcludes(result);
                }
            }
            return new ExcludeFieldInfoWrapper().setExcludes(Collections.emptySet());
        }
        Set<ExcludeFieldInfo> result = new HashSet<>();
        TableInfo tableInfo = pojoTableManager.getByPojoType(pojoType).getTableInfo();

        if (ignorer.getIgnoreModel() == IgnoreModel.EXCLUDE) {
            tableInfo.getFieldInfoList().stream().filter(f -> CollectionUtil.contains(ignoreFields, f.getJavaFieldName()))
                    .forEach(info -> result.add(new ExcludeFieldInfo(null, info)));
            this.exclude(tableInfo, ignoreFields, nestedChainList, result);
        } else if(ignorer.getIgnoreModel() == IgnoreModel.INCLUDE) {
            this.renderFilterIncludeFields(null, null, ignoreFields, tableInfo.getFieldInfoList(), result);
            this.include(null, tableInfo, ignoreFields, nestedChainList, result);
        }
        Set<ExcludeFieldInfo> excludeChainNode = result.stream().filter(ExcludeFieldInfo::excludeChain).collect(Collectors.toSet());
        result.removeIf(e -> {
            for (ExcludeFieldInfo excludeNode : excludeChainNode) {
                if (e.excludeChainField() && e.getChain().equals(excludeNode.getChain())) {
                    return true;
                }
            }
            return false;
        });
        return new ExcludeFieldInfoWrapper().setExcludes(result);
    }

    /**
     * 排除模式，排除ignoreFields中所有的字段
     * @param tableInfo pojo表映射信息
     * @param ignoreFields 忽略字段
     * @param nestedChainList 嵌套的表链
     * @param result 记录排除字段信息的变量
     */
    private void exclude(TableInfo tableInfo, String[] ignoreFields, List<TableChain> nestedChainList, Set<ExcludeFieldInfo> result) {
        if (tableInfo.isNested()) {
            for (String excludeField : ignoreFields) {
                if (excludeField.contains(".")) {
                    TableFieldInfoWrapper infoWrapper = this.getNestedFiledByName(nestedChainList, excludeField, false);
                    if (null == infoWrapper) {
                        TableChain chainByPath = this.getChainByPath(nestedChainList, excludeField);
                        if (null != chainByPath) {
                            result.add(new ExcludeFieldInfo(chainByPath, null));
                        }
                    } else {
                        result.add(new ExcludeFieldInfo(infoWrapper.getTableChain(), infoWrapper.getFieldInfo()));
                    }
                } else {
                    TableFieldInfo info = this.getByName(tableInfo, excludeField, false);
                    if (null == info) {
                        TableChain chainByPath = this.getChainByPath(nestedChainList, excludeField);
                        if (null != chainByPath) {
                            result.add(new ExcludeFieldInfo(chainByPath, null));
                        }
                    }
                }
            }
        }
    }

    /**
     * 递归查找指定表链节点
     * @param nestedChainList 嵌套的表链结构
     * @param fieldName 属性名
     * @return 目标表链
     */
    private TableChain getChainByPath(List<TableChain> nestedChainList, String fieldName) {
        int i = fieldName.indexOf(".");
        String curNode = i == -1 ? fieldName : fieldName.substring(0, i);
        String nextNode = i == -1 ? null : fieldName.substring(i + 1);
        for (TableChain chain : nestedChainList) {
            PojoMapping pojoMapping = chain.getPojoMapping();
            if (pojoMapping.getFieldInfo().getJavaFieldName().equals(curNode)) {
                if (StringUtil.isNotBlank(nextNode) && chain.hasChain()) {
                    return this.getChainByPath(chain.getChain(), nextNode);
                } else if (StringUtil.isBlank(nextNode)) {
                    return chain;
                }
            }
        }
        return null;
    }

    /**
     * 包含模式，排除不在ignoreFields中的所有字段（包括映射对象）
     * @param path 上一节点完整路径
     * @param tableInfo pojo表映射信息
     * @param ignoreFields 忽略字段
     * @param nestedChainList 嵌套的表链结构
     * @param result 记录排除字段信息的变量
     */
    private void include(String path, TableInfo tableInfo, String[] ignoreFields, List<TableChain> nestedChainList, Set<ExcludeFieldInfo> result) {
        if (tableInfo.isNested()) {
            for (TableChain chain : nestedChainList) {
                String chainPath = this.assembleName(path, chain);
                if (!this.containsChain(ignoreFields, chainPath)) {
                    result.add(new ExcludeFieldInfo(chain, null));
                    continue;
                }
                this.renderFilterIncludeFields(chainPath, chain, ignoreFields, pojoTableManager.getByPojoType(chain.getPojoType()).getTableInfo().getFieldInfoList(), result);
                if (chain.hasChain()) {
                    this.include(chainPath, tableInfo, ignoreFields, chain.getChain(), result);
                }
            }
        }
    }

    private void renderFilterIncludeFields(String path, TableChain chain, String[] ignoreFields, List<TableFieldInfo> fieldInfoList, Set<ExcludeFieldInfo> result) {
        List<TableFieldInfo> match = fieldInfoList.stream().filter(f -> CollectionUtil.contains(ignoreFields, this.assembleName(path, f))).collect(Collectors.toList());
        if (!match.isEmpty()) {
            List<TableFieldInfo> notMatch = fieldInfoList.stream().filter(f -> !CollectionUtil.contains(ignoreFields, this.assembleName(path, f))).collect(Collectors.toList());
            result.addAll(notMatch.stream().map(f -> new ExcludeFieldInfo(chain, f)).collect(Collectors.toList()));
        }
    }

    private String assembleName(String path, TableFieldInfo fieldInfo) {
        if (null == path) {
            return fieldInfo.getJavaFieldName();
        }
        return path + "." + fieldInfo.getJavaFieldName();
    }

    private String assembleName(String path, TableChain chain) {
        if (null == path) {
            return chain.getBelongField().getJavaFieldName();
        }
        return path + "." + chain.getBelongField().getJavaFieldName();
    }

    private boolean containsChain(String[] fields, String chainName) {
        if (!CollectionUtil.contains(fields, chainName)) {
            for (String field : fields) {
                if (BeanFieldUtil.isBelonged(chainName, field)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean pojoFieldOnly(Class<?> pojoType, String[] fieldsName) {
        for (String name : fieldsName) {
            if (name.contains(".")) {
                return false;
            }
            this.getByName(pojoType, name, true);
        }
        return true;
    }
}
