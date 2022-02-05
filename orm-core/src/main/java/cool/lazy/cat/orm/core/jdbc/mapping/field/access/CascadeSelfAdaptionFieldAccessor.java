package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.PojoFieldMapper;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author: mahao
 * @date: 2021/8/2 15:52
 */
public class CascadeSelfAdaptionFieldAccessor extends AbstractFieldAccessor implements FieldAccessor {

    public CascadeSelfAdaptionFieldAccessor(TableInfo tableInfo) {
        super(tableInfo);
    }

    @Override
    public void init() {
        if (super.initialized) {
            return;
        }
        List<PojoMapping> mappings = super.tableInfo.getMappings();
        super.initAnnotationIgnoreFields(mappings);
        super.analysisIgnore();
        for (PojoMapping mapping : mappings) {
            String lowerField = mapping.getPojoField().getJavaFieldName();
            if (CollectionUtil.isNotEmpty(mapping.getCascadeScope())) {
                this.calculationByScope(super.initScopes(Arrays.asList(mapping.getCascadeScope())), super.mainTableNode, super.tableInfo.getFieldMapper().getMapperByName(lowerField), Collections.singletonList(lowerField), mapping);
            } else {
                this.calculationByLevel(super.mainTableNode, super.tableInfo.getFieldMapper().getMapperByName(lowerField), Collections.singletonList(lowerField), mapping, 0, mapping.getCascadeLevel());
            }
        }
        super.init();
    }

    private void calculationByLevel(TableNode belongPojoTable, PojoFieldMapper quoteMapper, List<String> pathPrefix, PojoMapping mapping, int curDepth, int maxDepth) {
        if (mapping.getCascadeLevel() < 1) {
            return;
        }
        PathDescriptorImpl nodePath = new PathDescriptorImpl(pathPrefix);
        if (super.ignoreNode(nodePath)) {
            return;
        }
        TableNode tableNode = new TableNodeImpl(nodePath, quoteMapper.getTableInfo().getSchema(), quoteMapper.getTableInfo().getName(), quoteMapper.getTableInfo().getPojoType(), belongPojoTable, super.buildPojoMapping(belongPojoTable, mapping));
        super.addNode(tableNode, belongPojoTable);
        for (PojoField pojoField : quoteMapper.getTableInfo().getFieldInfoMap().values()) {
            List<String> fullPath = new ArrayList<>(pathPrefix);
            fullPath.add(pojoField.getJavaFieldName());
            super.addField(new FieldDescriptorImpl(new PathDescriptorImpl(fullPath), pojoField, tableNode, super.tableNodeMapping.size() -1));
        }
        curDepth ++;
        if (CollectionUtil.isNotEmpty(quoteMapper.getTableInfo().getMappings()) && curDepth < maxDepth) {
            for (PojoMapping quoteMapping : quoteMapper.getTableInfo().getMappings()) {
                List<String> fullPath = new ArrayList<>(pathPrefix);
                String lowerField = quoteMapping.getPojoField().getJavaFieldName();
                fullPath.add(lowerField);
                this.calculationByLevel(tableNode, quoteMapper.getMapperByName(lowerField), fullPath, quoteMapping, curDepth, maxDepth);
            }
        }
    }

    protected void calculationByScope(Set<String> scopes, TableNode belongPojoTable, PojoFieldMapper quoteMapper, List<String> pathPrefix, PojoMapping mapping) {
        PathDescriptorImpl nodePath = new PathDescriptorImpl(pathPrefix);
        if (super.ignoreNode(nodePath)) {
            return;
        }
        TableNode tableNode = new TableNodeImpl(nodePath, quoteMapper.getTableInfo().getSchema(), quoteMapper.getTableInfo().getName(), quoteMapper.getTableInfo().getPojoType(), belongPojoTable, super.buildPojoMapping(belongPojoTable, mapping));
        super.addNode(tableNode, belongPojoTable);
        for (PojoField pojoField : quoteMapper.getTableInfo().getFieldInfoMap().values()) {
            List<String> fullPath = new ArrayList<>(pathPrefix);
            fullPath.add(pojoField.getJavaFieldName());
            super.addField(new FieldDescriptorImpl(new PathDescriptorImpl(fullPath), pojoField, tableNode, super.tableNodeMapping.size() -1));
        }
        if (CollectionUtil.isNotEmpty(quoteMapper.getTableInfo().getMappings())) {
            for (PojoMapping quoteMapping : quoteMapper.getTableInfo().getMappings()) {
                List<String> fullPath = new ArrayList<>(pathPrefix);
                String lowerField = quoteMapping.getPojoField().getJavaFieldName();
                fullPath.add(lowerField);
                if (scopes.contains(String.join(".", fullPath))) {
                    this.calculationByScope(scopes, tableNode, quoteMapper.getMapperByName(lowerField), fullPath, quoteMapping);
                }
            }
        }
    }
}
