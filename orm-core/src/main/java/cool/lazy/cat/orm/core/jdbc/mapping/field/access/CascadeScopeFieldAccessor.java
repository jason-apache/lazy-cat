package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.PojoFieldMapper;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/8/26 16:23
 */
public class CascadeScopeFieldAccessor extends AbstractFieldAccessor implements FieldAccessor {

    protected final Set<String> scopes;

    public CascadeScopeFieldAccessor(TableInfo tableInfo, Collection<String> scopes) {
        super(tableInfo);
        if (null == scopes) {
            throw new IllegalArgumentException("查询范围不能为空");
        }
        this.scopes = super.initScopes(scopes);
    }

    @Override
    public void init() {
        if (super.initialized) {
            return;
        }
        // 排序, 按照查询范围中字段出现的顺序
        List<String> scopeArray = new ArrayList<>(this.scopes);
        List<PojoMapping> ordinalMappings = super.tableInfo.getMappings().stream().sorted(Comparator.comparing(m -> scopeArray.indexOf(m.getPojoField().getJavaFieldName()))).collect(Collectors.toList());
        super.initAnnotationIgnoreFields(ordinalMappings);
        super.analysisIgnore();
        for (PojoMapping mapping : ordinalMappings) {
            String lowerField = mapping.getPojoField().getJavaFieldName();
            if (this.scopes.contains(lowerField)) {
                this.calculation(super.mainTableNode, super.tableInfo.getFieldMapper().getMapperByName(lowerField), Collections.singletonList(lowerField), mapping);
            }
        }
        super.init();
    }

    protected void calculation(TableNode belongPojoTable, PojoFieldMapper quoteMapper, List<String> pathPrefix, PojoMapping mapping) {
        PathDescriptorImpl nodePath = new PathDescriptorImpl(pathPrefix);
        if (super.ignoreNode(nodePath)) {
            return;
        }
        TableNode tableNode = new TableNodeImpl(nodePath, quoteMapper.getTableInfo().getSchema(), quoteMapper.getTableInfo().getName(), quoteMapper.getTableInfo().getPojoType(), belongPojoTable, super.buildPojoMapping(belongPojoTable, mapping));
        super.addNode(tableNode, belongPojoTable);
        for (PojoField fieldInfo : quoteMapper.getTableInfo().getFieldInfoMap().values()) {
            List<String> fullPath = new ArrayList<>(pathPrefix);
            fullPath.add(fieldInfo.getJavaFieldName());
            super.addField(new FieldDescriptorImpl(new PathDescriptorImpl(fullPath), fieldInfo, tableNode, super.tableNodeMapping.size() -1));
        }
        if (CollectionUtil.isNotEmpty(quoteMapper.getTableInfo().getMappings())) {
            for (PojoMapping quoteMapping : quoteMapper.getTableInfo().getMappings()) {
                List<String> fullPath = new ArrayList<>(pathPrefix);
                String lowerField = quoteMapping.getPojoField().getJavaFieldName();
                fullPath.add(lowerField);
                if (this.scopes.contains(String.join(".", fullPath))) {
                    this.calculation(tableNode, quoteMapper.getMapperByName(lowerField), fullPath, quoteMapping);
                }
            }
        }
    }
}
