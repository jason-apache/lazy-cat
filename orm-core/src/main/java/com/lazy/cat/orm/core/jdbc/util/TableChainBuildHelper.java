package com.lazy.cat.orm.core.jdbc.util;


import com.lazy.cat.orm.core.base.util.CollectionUtil;
import com.lazy.cat.orm.core.jdbc.dto.CascadeLevelMapper;
import com.lazy.cat.orm.core.jdbc.dto.CountHolder;
import com.lazy.cat.orm.core.jdbc.generator.AliasNameGenerator;
import com.lazy.cat.orm.core.jdbc.holder.TableChainHolder;
import com.lazy.cat.orm.core.jdbc.manager.subject.PojoTableSubject;
import com.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import com.lazy.cat.orm.core.jdbc.mapping.TableChain;
import com.lazy.cat.orm.core.jdbc.mapping.TableInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: mahao
 * @date: 2021/4/13 08:58
 * 协助tableChain初始化工具类
 */
public final class TableChainBuildHelper {

    public static final String ALL = "ALL_$" + UUID.randomUUID().toString();
    private static AliasNameGenerator aliasNameGenerator;

    public static void initAliasNameGenerator(AliasNameGenerator a) {
        if (null == aliasNameGenerator) {
            aliasNameGenerator = a;
        }
    }

    /**
     * 生成tableChain
     * @param tableInfo 当前操作表对象
     * @param mappings 一对多、一对一、多对一映射集
     * @param allSubjectMap 所有pojo的table映射关系
     * @param cascadeLevelMapper 当前操作表对象的级联关系映射
     * @return 数据库表链式调用结构
     */
    public static TableChainHolder build(TableInfo tableInfo, List<PojoMapping> mappings, Map<Class<?>, PojoTableSubject> allSubjectMap,
                                         CascadeLevelMapper cascadeLevelMapper) {
        TableChainHolder holder = new TableChainHolder(tableInfo);
        List<TableChain> nestedChain = new ArrayList<>();
        for (PojoMapping mapping : mappings) {
            nestedChain.addAll(analysisTableChain(new ArrayList<>(), mapping, null, allSubjectMap, cascadeLevelMapper.getLevel(mapping)));
        }
        List<TableChain> flatChain = getFlatChain(new ArrayList<>(), nestedChain, new CountHolder(0));
        return holder.setNestedChain(nestedChain).setFlatChain(flatChain);
    }

    /**
     * 递归解析关联表
     * @param chainList 记录完整链路的变量
     * @param pojoMapping 当前处理的表映射关系
     * @param upperChain 上一层级的tableChain
     * @param allSubjectMap 所有pojo的table映射关系
     * @param depth 当前遍历深度
     * @return 完整链路
     */
    private static List<TableChain> analysisTableChain(List<TableChain> chainList, PojoMapping pojoMapping, TableChain upperChain,
                                                  Map<Class<?>, PojoTableSubject> allSubjectMap, int depth) {
        PojoTableSubject mappingSubject = allSubjectMap.get(pojoMapping.getJavaType());
        TableChain chain = buildTableChain(mappingSubject, pojoMapping);
        chain.setUpperChain(upperChain).setPojoMapping(pojoMapping);
        chainList.add(chain);
        depth --;
        if (depth <= 0) {
            return chainList;
        }
        TableInfo mappingTable = mappingSubject.getTableInfo();
        if (hasMapping(mappingSubject)) {
            List<PojoMapping> pojoMappingList = CollectionUtil.concat(mappingTable.getOneToOneMapping(), mappingTable.getOneToManyMapping(), mappingTable.getManyToOneMapping());
            List<TableChain> nestedChain = new ArrayList<>();
            for (PojoMapping mapping : pojoMappingList) {
                nestedChain.addAll(analysisTableChain(new ArrayList<>(), mapping, chain, allSubjectMap, depth));
            }
            chain.setChain(nestedChain);
        }
        return chainList;
    }

    private static TableChain buildTableChain(PojoTableSubject mappingSubject, PojoMapping pojoMapping) {
        TableChain chain = new TableChain().setPojoType(mappingSubject.getPojoType());
        chain.setName(mappingSubject.getTableInfo().getFullName());
        chain.setJoinCondition(pojoMapping.getJoinCondition());
        chain.setBelongField(pojoMapping.getFieldInfo());
        chain.setId(mappingSubject.getTableInfo().getId());
        chain.setPojoType(mappingSubject.getPojoType());
        return chain;
    }

    /**
     * 将嵌套的tableChain平铺，并生成表别名
     * @param flatChainList 记录完整平铺链路的变量
     * @param nestedChainList 嵌套的tableChain
     * @param holder 遍历深度持有对象
     * @return 完整链路
     */
    private static List<TableChain> getFlatChain(List<TableChain> flatChainList, List<TableChain> nestedChainList, CountHolder holder) {
        for (TableChain chain : nestedChainList) {
            holder.count ++;
            chain.setAliasName(aliasNameGenerator.generatorTableName(chain.getPojoMapping().getFieldInfo().getJavaFieldName(), holder.count));
            chain.setFlatIndex(holder.count);
            flatChainList.add(chain);
            if (chain.hasChain()) {
                getFlatChain(flatChainList, chain.getChain(), holder);
            }
        }
        return flatChainList;
    }

    private static boolean hasMapping(PojoTableSubject subject) {
        return subject.getTableInfo().getOneToOneMapping() != null || subject.getTableInfo().getOneToManyMapping() != null
                || subject.getTableInfo().getManyToOneMapping() != null;
    }
}
