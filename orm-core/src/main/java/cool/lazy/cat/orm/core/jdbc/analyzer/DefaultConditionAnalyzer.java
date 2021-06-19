package cool.lazy.cat.orm.core.jdbc.analyzer;


import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.base.util.Ignorer;
import cool.lazy.cat.orm.core.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.KeyWordConverter;
import cool.lazy.cat.orm.core.jdbc.condition.Condition;
import cool.lazy.cat.orm.core.jdbc.dto.TableFieldInfoWrapper;
import cool.lazy.cat.orm.core.jdbc.exception.UnKnowFiledException;
import cool.lazy.cat.orm.core.jdbc.holder.SearchSqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.holder.SqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.holder.UpdateSqlParamHolder;
import cool.lazy.cat.orm.core.jdbc.mapping.TableChain;
import cool.lazy.cat.orm.core.jdbc.mapping.TableFieldInfo;
import cool.lazy.cat.orm.core.jdbc.param.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2021/3/13 20:49
 */
public class DefaultConditionAnalyzer implements ConditionAnalyzer {

    @Autowired
    protected FieldInfoCatcher fieldInfoCatcher;
    @Autowired
    protected KeyWordConverter keyWordConverter;
    @Autowired
    protected ExpressionAdapter expressionAdapter;

    @Override
    public SearchSqlParamHolder analysis(SearchParam searchParam) {
        if (Condition.EMPTY_CONDITION == searchParam.getCondition() || (null == searchParam.getCondition() && null == searchParam.getParams())) {
            return new SearchSqlParamHolder(new StringBuilder(), Collections.emptyMap());
        }
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params;
        if (searchParam.getCondition() != null) {
            params = new HashMap<>();
            this.analysis(searchParam.getPojoType(), sql, searchParam.getNestedChain(), searchParam.getCondition(), params,
                    true, Ignorer.getFields(searchParam.getIgnorer()), true, null);
        } else {
            params = searchParam.getParams();
            this.analysis(searchParam.getPojoType(), sql, searchParam.getNestedChain(), params, Ignorer.getFields(searchParam.getIgnorer()));
        }
        return new SearchSqlParamHolder(sql, params);
    }

    @Override
    public SqlParamHolder analysis(Class<?> pojoType, Condition condition) {
        StringBuilder where = new StringBuilder();
        UpdateSqlParamHolder holder = new UpdateSqlParamHolder(where);
        if (null != condition) {
            Map<String, Object> param = new HashMap<>();
            this.analysis(pojoType, where, null, condition, param, true, null, false, this.buildHook());
            holder.setParam(param);
        }
        if (where.length() > 0) {
            where.insert(0, keyWordConverter.where());
        }
        return holder;
    }

    /**
     * 解析map中的条件
     * @param pojoType pojo类型
     * @param sql 原始sql
     * @param nestedChainList 嵌套的表链结构
     * @param params 条件字段
     * @param excludeFields 忽略字段
     */
    protected void analysis(Class<?> pojoType, StringBuilder sql, List<TableChain> nestedChainList, Map<String, Object> params, String[] excludeFields) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (StringUtil.isBlank(entry.getKey()) || null == entry.getValue() || StringUtil.isBlank(entry.getValue().toString())) {
                continue;
            }
            if (CollectionUtil.contains(excludeFields, entry.getKey())) {
                continue;
            }
            boolean isNested = entry.getKey().contains(".");
            String tableName = null;
            TableFieldInfo fieldInfo = null;
            if (isNested) {
                TableFieldInfoWrapper wrapper = fieldInfoCatcher.getNestedFiledByName(nestedChainList, entry.getKey(), false);
                if (null != wrapper && wrapper.getFieldInfo().havingQueryFilter()) {
                    tableName = wrapper.getTableChain().getAliasName();
                    fieldInfo = wrapper.getFieldInfo();
                }
            } else {
                tableName = JdbcConstant.MAIN_TABLE_NAME;
                fieldInfo = fieldInfoCatcher.getByName(pojoType, entry.getKey(), false);
            }
            if (null != fieldInfo) {
                sql.append(keyWordConverter.and()).append(tableName).append(".").append(fieldInfo.getDbFieldName()).append(" ");
                expressionAdapter.adapterConditionSymbol(fieldInfo.getColumn().getQueryFilterType(), sql, entry.getKey());
            }
        }
        if (sql.length() > 0) {
            sql.replace(0, keyWordConverter.and().length(), "");
        }
    }

    /**
     * 递归解析条件并拼接sql
     * @param pojoType pojo类型
     * @param sql 原始sql
     * @param nestedChainList 嵌套的表链结构
     * @param condition 条件
     * @param params 条件记录的持有对象，参数名与参数值的映射
     * @param strictModel 严格模式
     * @param excludeFields 排除字段
     * @param columnAliasName 是否启用列别名
     * @param hook 钩子方法，执行额外的逻辑
     */
    protected void analysis(Class<?> pojoType, StringBuilder sql, List<TableChain> nestedChainList, Condition condition, Map<String, Object> params, boolean strictModel,
                            String[] excludeFields, boolean columnAliasName, Hook hook) {
        if (null != hook) {
            hook.call(pojoType, condition);
        }
        this.render(pojoType, sql, nestedChainList, condition, params, strictModel, excludeFields, columnAliasName);
        if (null != condition.getAnd()) {
            sql.append(keyWordConverter.and()).append(" ( ");
            int count = 0;
            for (Condition and : condition.getAnd()) {
                if (count > 0) {
                    sql.append(keyWordConverter.and());
                }
                sql.append(" (");
                this.analysis(pojoType, sql, nestedChainList, and, params, strictModel, excludeFields, columnAliasName, hook);
                sql.append(") ");
                count ++;
            }
            sql.append(" ) ");
        }
        if (null != condition.getOr()) {
            sql.append(keyWordConverter.or()).append(" ( ");
            int count = 0;
            for (Condition or : condition.getOr()) {
                if (count > 0) {
                    sql.append(keyWordConverter.or());
                }
                sql.append(" (");
                this.analysis(pojoType, sql, nestedChainList, or, params, strictModel, excludeFields, columnAliasName, hook);
                sql.append(") ");
                count ++;
            }
            sql.append(" ) ");
        }
    }

    /**
     * 注入参数名与参数值的映射
     * @param pojoType pojo类型
     * @param sql 原始sql
     * @param nestedChainList 嵌套的表链结构
     * @param condition 条件
     * @param params 参数名与参数值的映射
     * @param strictModel 严格模式
     * @param excludeFields 排除字段
     * @param tableAliasName 是否启用表别名
     */
    protected void render(Class<?> pojoType, StringBuilder sql, List<TableChain> nestedChainList, Condition condition, Map<String, Object> params, boolean strictModel,
                          String[] excludeFields, boolean tableAliasName) {
        String tableName;
        TableFieldInfo fieldInfo;
        if (condition.getField().contains(".")) {
            TableFieldInfoWrapper wrapper = fieldInfoCatcher.getNestedFiledByName(nestedChainList, condition.getField(), false);
            if (null != wrapper) {
                boolean contains = CollectionUtil.contains(excludeFields, wrapper.getFieldInfo().getJavaFieldName());
                if (strictModel && contains) {
                    throw new UnKnowFiledException("从表对象已被忽略：" + wrapper.getTableChain().getBelongField().getJavaFieldName());
                }
                if (contains) {
                    return;
                }
                tableName = wrapper.getTableChain().getAliasName();
                fieldInfo = wrapper.getFieldInfo();
            } else {
                if (strictModel) {
                    throw new UnKnowFiledException("嵌套的属性，不存在的字段：" + condition.getField() + "\t请检查cascadeLevel");
                }
                return;
            }
        } else {
            fieldInfo = fieldInfoCatcher.getByName(pojoType, condition.getField(), false);
            if (null == fieldInfo) {
                if (strictModel) {
                    throw new UnKnowFiledException("pojo不存在该字段：" + condition.getField());
                }
                return;
            }
            tableName = JdbcConstant.MAIN_TABLE_NAME;
        }
        if (tableAliasName) {
            sql.append(tableName).append(".");
        }
        sql.append(fieldInfo.getDbFieldName()).append(" ");
        int length = sql.length();
        String fieldName = this.paramUniqueKey(condition.getField(), length);
        expressionAdapter.adapterConditionSymbol(condition.getType(), sql, fieldName);
        sql.append(" ");
        params.put(fieldName, condition.getValue());
    }

    private String paramUniqueKey(String key, int len) {
        if (len != -1) {
            key += len + "`";
        }
        return key;
    }

    @FunctionalInterface
    interface Hook {
        /**
         * 植入一个钩子方法，执行额外的逻辑
         * @param pojoType pojo类型
         * @param condition 条件
         */
        void call(Class<?> pojoType, Condition condition);
    }

    /**
     * 生成一个钩子方法，修改、新增时进行额外检查
     * @return 钩子方法
     */
    private Hook buildHook() {
        return (pojoType, condition) -> {
            boolean pojoFiledOnly = fieldInfoCatcher.pojoFieldOnly(pojoType, new String[]{condition.getField()});
            if (!pojoFiledOnly) {
                throw new UnsupportedOperationException("修改|删除操作只允许操作本对象中的属性 #" + pojoType.getName() + "#" + condition.getField());
            }
        };
    }
}
