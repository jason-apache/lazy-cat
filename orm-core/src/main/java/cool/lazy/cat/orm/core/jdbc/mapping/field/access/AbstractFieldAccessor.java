package cool.lazy.cat.orm.core.jdbc.mapping.field.access;

import cool.lazy.cat.orm.core.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.jdbc.IgnoreModel;
import cool.lazy.cat.orm.core.jdbc.Ignorer;
import cool.lazy.cat.orm.core.jdbc.constant.JdbcConstant;
import cool.lazy.cat.orm.core.jdbc.exception.UnKnowFiledException;
import cool.lazy.cat.orm.core.jdbc.generator.impl.DefaultAliasNameGenerator;
import cool.lazy.cat.orm.core.jdbc.mapping.ManyToOneMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.OneToManyMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.OneToOneMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.PojoMapping;
import cool.lazy.cat.orm.core.jdbc.mapping.TableInfo;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.IdField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/8/2 15:39
 */
public abstract class AbstractFieldAccessor implements FieldAccessor {

    /**
     * 所有表结构
     */
    protected final Map<String, TableNode> tableNodeMapping = new LinkedHashMap<>();
    /**
     * 根对象表信息
     */
    protected final TableInfo tableInfo;
    /**
     * 根对象表结构
     */
    protected final TableNode mainTableNode;
    /**
     * 所有字段
     */
    protected Map<String, FieldDescriptor> allFieldMapping = new LinkedHashMap<>();
    /**
     * 忽略访问字段
     */
    protected Ignorer ignorer;
    /**
     * 记录忽略了哪些资源
     */
    protected Set<String> ignoreResources = Collections.emptySet();
    protected Class<?>[] tableLinkOrderRecommend;
    protected Class<?>[] tableLinkOrderRecommendReversed;
    /**
     * 是否已初始化
     */
    protected boolean initialized;

    protected AbstractFieldAccessor(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        // 解析根对象表信息
        this.mainTableNode = new TableNodeImpl(new PathDescriptorImpl(Collections.singletonList("")), tableInfo.getSchema(), tableInfo.getName(), tableInfo.getPojoType(), null, null);
        this.addNode(mainTableNode, null);
        this.mainTableNode.setTableAliasName(JdbcConstant.MAIN_TABLE_NAME);
        this.tableInfo.getFieldInfoMap().values().forEach((f) -> this.addField(new FieldDescriptorImpl(new PathDescriptorImpl(Collections.singletonList(f.getJavaFieldName())), f, this.mainTableNode, 0)));
    }

    @Override
    public void init() {
        if (null != this.ignorer) {
            this.filterFieldByIgnoreResources();
        }
        int columnIndex = 0;
        for (TableNode tableNode : tableNodeMapping.values()) {
            for (FieldDescriptor fieldDescriptor : tableNode.getFieldMapping().values()) {
                if (fieldDescriptor.isIgnored()) {
                    continue;
                }
                fieldDescriptor.setColumnIndex(columnIndex ++);
            }
        }
        this.initTableLinkOrderRecommend();
        this.initialized = true;
    }

    /**
     * 初始化注解标注的忽略字段
     * @param mappings 对象映射结构集合
     */
    protected void initAnnotationIgnoreFields(List<PojoMapping> mappings) {
        // 收集所有需要忽略的字段
        Set<String> ignoreFields = mappings.stream().filter(m -> CollectionUtil.isNotEmpty(m.getIgnoreFields())).flatMap(m -> Arrays.stream(m.getIgnoreFields())).collect(Collectors.toSet());
        if (CollectionUtil.isNotEmpty(ignoreFields)) {
            if (null == this.ignorer) {
                this.setIgnore(Ignorer.build(ignoreFields));
            } else {
                this.ignorer.getFields().addAll(ignoreFields);
            }
        }
    }

    /**
     * 初始化关联范围
     * @param scopes 范围
     * @return 关联对象全集
     */
    protected Set<String> initScopes(Collection<String> scopes) {
        Set<String> scopesSet = new LinkedHashSet<>();
        for (String scopeDesc : scopes) {
            String[] paths = scopeDesc.split("\\.");
            if (paths.length > 1) {
                List<String> pathRecorder = new ArrayList<>();
                for (String str : paths) {
                    pathRecorder.add(str);
                    scopesSet.add(String.join(".", pathRecorder));
                }
            } else {
                scopesSet.add(paths[0]);
            }
        }
        return scopesSet;
    }

    protected void analysisIgnore() {
        if (null == this.ignorer) {
            return;
        }
        if (CollectionUtil.isNotEmpty(this.ignorer.getFields())) {
            if (IgnoreModel.EXCLUDE == this.ignorer.getIgnoreModel()) {
                // 排除模式
                this.ignoreResources = this.ignorer.getFields();
            } else if(IgnoreModel.INCLUDE == this.ignorer.getIgnoreModel()) {
                // 包含模式
                this.ignoreResources = new HashSet<>();
                for (String ignoreField : this.ignorer.getFields()) {
                    String[] paths = ignoreField.split("\\.");
                    // 存在多个节点则需要保留全集
                    if (paths.length > 1) {
                        List<String> pathRecorder = new ArrayList<>();
                        for (String str : paths) {
                            pathRecorder.add(str);
                            this.ignoreResources.add(String.join(".", pathRecorder));
                        }
                    } else {
                        this.ignoreResources.add(paths[0]);
                    }
                }
            }
        }
    }

    @Override
    public List<FieldDescriptor> getFieldDescriptors() {
        return allFieldMapping.values().stream().filter(f -> !f.isIgnored()).collect(Collectors.toList());
    }

    /**
     * 添加一个字段
     * @param fieldDescriptor 字段描述
     */
    protected void addField(FieldDescriptor fieldDescriptor) {
        TableNode tableNode = fieldDescriptor.getTableNode();
        if (tableNode.getFieldMapping() == null) {
            tableNode.setFieldMapping(new LinkedHashMap<>());
        }
        fieldDescriptor.setAliasName(DefaultAliasNameGenerator.instance.generatorFiledName(fieldDescriptor.getJavaFieldName(), tableNode.getFieldMapping().size()));
        tableNode.getFieldMapping().put(fieldDescriptor.getJavaFieldName(), fieldDescriptor);
        if (fieldDescriptor.getRealField() instanceof IdField) {
            tableNode.setIdDescriptor(fieldDescriptor);
        }
        allFieldMapping.put(fieldDescriptor.getPath().getFullPath(), fieldDescriptor);
    }

    /**
     * 构建pojoMapping对象
     * 只有父节点的insert|update|delete权限开放时 子节点的@PojoMapping注解标注的insert|update|delete权限控制才有意义
     * @param belongTableNode 归属对象表结构
     * @param curNodePojoMapping 当前对象映射
     * @return pojoMapping
     */
    protected PojoMapping buildPojoMapping(TableNode belongTableNode, PojoMapping curNodePojoMapping) {
        if (null == belongTableNode.getPojoMapping()) {
            return curNodePojoMapping;
        }
        PojoMapping belongTableNodePojoMapping = belongTableNode.getPojoMapping();
        boolean insertable = belongTableNodePojoMapping.isInsertable();
        boolean updatable = belongTableNodePojoMapping.isUpdatable();
        boolean deletable = belongTableNodePojoMapping.isDeletable();
        FakePojoMapping fakePojoMapping = this.buildFakePojoMapping(curNodePojoMapping);
        if (insertable) {
            fakePojoMapping.setInsertable(curNodePojoMapping.isInsertable());
        }
        if (updatable) {
            fakePojoMapping.setUpdatable(curNodePojoMapping.isUpdatable());
        }
        if (deletable) {
            fakePojoMapping.setDeletable(curNodePojoMapping.isDeletable());
        }
        return fakePojoMapping;
    }

    protected FakePojoMapping buildFakePojoMapping(PojoMapping pojoMapping) {
        if (pojoMapping instanceof OneToManyMapping) {
            return new FakeOneToManyPojoMappingImpl((OneToManyMapping) pojoMapping);
        }
        if (pojoMapping instanceof ManyToOneMapping) {
            return new FakeManyToOnePojoMappingImpl((ManyToOneMapping) pojoMapping);
        }
        if (pojoMapping instanceof OneToOneMapping) {
            return new FakeOneToOnePojoMappingImpl((OneToOneMapping) pojoMapping);
        }
        throw new UnsupportedOperationException("不支持的实现类: " + pojoMapping.getClass());
    }

    /**
     * 添加一个表结构
     * @param tableNode 表结构
     * @param belongPojoTable 所属表
     */
    protected void addNode(TableNode tableNode, TableNode belongPojoTable) {
        tableNodeMapping.put(tableNode.getPath().getFullPath(), tableNode);
        tableNode.setIndex(tableNodeMapping.size() -1);
        if (null != belongPojoTable) {
            tableNode.setTableAliasName(DefaultAliasNameGenerator.instance.generatorTableName(tableNode.tableName(), tableNodeMapping.size() -1));
            if (null == belongPojoTable.getChildren()) {
                belongPojoTable.setChildren(new ArrayList<>());
            }
            belongPojoTable.getChildren().add(tableNode);
        }
    }

    /**
     * 判断给定路径的资源是否被忽略访问
     * @param path 完整路径
     * @return 是否被忽略
     */
    protected boolean ignoreNode(PathDescriptor path) {
        if (null == this.ignorer) {
            return false;
        }
        boolean contains = this.ignoreResources.contains(path.getFullPath());
        return (IgnoreModel.EXCLUDE == this.ignorer.getIgnoreModel()) == contains;
    }

    /**
     * 根据计算好的 ignoreResources 设置字段为忽略访问
     */
    protected void filterFieldByIgnoreResources() {
        if (IgnoreModel.EXCLUDE == this.ignorer.getIgnoreModel()) {
            // 排除模式
            for (TableNode tableNode : tableNodeMapping.values()) {
                tableNode.getFieldMapping().values().forEach(f -> {
                    if (!f.forced()) {
                        f.setIgnored(this.ignoreResources.contains(f.getPath().getFullPath()));
                    }
                });
            }
        } else if (IgnoreModel.INCLUDE == this.ignorer.getIgnoreModel()) {
            // 包含模式 需要计算保留了哪些字段 然后将不在这些字段内的字段标记为忽略
            Map<String, Set<String>> retain = new HashMap<>();
            for (TableNode tableNode : tableNodeMapping.values()) {
                for (FieldDescriptor field : tableNode.getFieldMapping().values()) {
                    if (this.ignoreResources.contains(field.getPath().getFullPath())) {
                        retain.computeIfAbsent(tableNode.getPath().getFullPath(), f -> new HashSet<>()).add(field.getPath().getFullPath());
                    }
                }
            }
            for (TableNode tableNode : tableNodeMapping.values()) {
                Set<String> retainFields = retain.get(tableNode.getPath().getFullPath());
                if (CollectionUtil.isNotEmpty(retainFields)) {
                    tableNode.getFieldMapping().values().forEach(f -> {
                        if (!f.forced()) {
                            f.setIgnored(!retainFields.contains(f.getPath().getFullPath()));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void setIgnore(Ignorer ignorer) {
        if (null == ignorer || null == ignorer.getIgnoreModel()) {
            throw new IllegalArgumentException("无效参数: ignore字段为空");
        }
        this.ignorer = ignorer;
    }

    @Override
    public TableNode getRootTableNode() {
        return this.mainTableNode;
    }

    @Override
    public Map<String, TableNode> getTableNodeMapping() {
        return this.tableNodeMapping;
    }

    @Override
    public FieldDescriptor get(String name) {
        FieldDescriptor fieldDescriptor = this.allFieldMapping.get(name);
        if (null == fieldDescriptor) {
            throw new UnKnowFiledException("pojo不存在该字段: " + name);
        }
        return fieldDescriptor;
    }

    @Override
    public boolean hasOneToManyMapping() {
        return this.hasOneToManyMapping(this.mainTableNode);
    }

    protected boolean hasOneToManyMapping(TableNode node) {
        if (node.getPojoMapping() instanceof OneToManyMapping) {
            return true;
        }
        if (CollectionUtil.isEmpty(node.getChildren())) {
            return false;
        }
        for (TableNode child : node.getChildren()) {
            if (this.hasOneToManyMapping(child)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean nested() {
        return CollectionUtil.isNotEmpty(mainTableNode.getChildren());
    }

    @Override
    public String toString() {
        return this.tableInfo.toString();
    }

    @Override
    public Class<?>[] getTableLinkOrderRecommend(boolean reversed) {
        return reversed ? this.tableLinkOrderRecommendReversed : this.tableLinkOrderRecommend;
    }

    protected void initTableLinkOrderRecommend() {
        if (tableNodeMapping.values().stream().map(TableNode::getPojoMapping).anyMatch(m -> null != m && m.havingMappedToSource())) {
            Block[] blocks = this.sort(this.collecting());
            Map<Class<?>, Integer> sortMap = new HashMap<>(blocks.length);
            for (Block block : blocks) {
                Integer sort = sortMap.get(block.getTableNode().getPojoType());
                // 记录下标最大的数据
                if (null == sort || block.getIndex() > sort) {
                    sortMap.put(block.getTableNode().getPojoType(), block.getIndex());
                }
            }
            this.tableLinkOrderRecommend = sortMap.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).toArray(Class[]::new);
            // 反转
            this.tableLinkOrderRecommendReversed = new Class<?>[tableLinkOrderRecommend.length];
            for (int i = tableLinkOrderRecommend.length -1, j = 0; i >= 0; i--, j++) {
                tableLinkOrderRecommendReversed[j] = tableLinkOrderRecommend[i];
            }
        }
    }

    protected Block[] collecting() {
        Block[] blocks = new Block[this.tableNodeMapping.size()];
        this.collecting(this.mainTableNode, blocks, new Counter(), new Counter(), null);
        return blocks;
    }

    /**
     * 递归收集全部子节点
     * @param entry 遍历入口
     * @param result 存放了所有数据的平铺容器 需要输出至这个容器
     * @param counter 下标计数器
     * @param childrenCounter 子节点数量计数器
     * @param belong 归属节点
     */
    protected void collecting(TableNode entry, Block[] result, Counter counter, Counter childrenCounter, Block belong) {
        Block block = new Block(entry, belong, counter.getVal());
        result[counter.getVal()] = block;
        counter.increment();
        if (CollectionUtil.isNotEmpty(entry.getChildren())) {
            for (TableNode child : entry.getChildren()) {
                childrenCounter.increment();
                Counter cc = new Counter();
                this.collecting(child, result, counter, cc, block);
                childrenCounter.increment(cc.getVal());
            }
        }
        // block长度 = 子节点数量 + 1 (本节点)
        block.setLength(childrenCounter.getVal() + 1);
    }

    /*protected void sort(Block[] blocksStub) {
        for (int i = 0; i < blocksStub.length; i++) {
            Block block = blocksStub[i];
            if (block.isResolved() || null == block.getTableNode().getPojoMapping() || !block.getTableNode().getPojoMapping().havingMappedToSource()) {
                continue;
            }
            // 把本节点需要移动的所有块复制到另一个容器中
            Block[] toBeMovedStub = new Block[block.getLength()];
            System.arraycopy(blocksStub, i, toBeMovedStub, 0, block.getLength());
            Block toBeReplaced = block.getBelong();
            // 计算原数组需要挪动的块的长度 也就是原数组中目标位置的块与本节点相距距离
            int range = Math.abs(toBeReplaced.getIndex() - block.getIndex());
            // 计算原数组需要挪动的块向后移动的目标位置 也就是本节点的尾部
            int dest = toBeReplaced.getIndex() + block.getLength();
            // 移动原数组中的数据 向后移动
            System.arraycopy(blocksStub, block.getBelong().getIndex(), blocksStub, dest, range);
            // 覆盖原数组中的块数据
            System.arraycopy(toBeMovedStub, 0, blocksStub, block.getBelong().getIndex(), toBeMovedStub.length);
            // 回归到原有位置继续向后处理 这个位置的数据已被替换成新的block (本节点)
            i = block.getBelong().getIndex();
            // 重新设置索引
            for (int j = 0; j < blocksStub.length; j++) {
                blocksStub[j].setIndex(j);
            }
            block.setResolved(true);
        }
    }*/

    /**
     * 对数据块进行排序
     * @param blocksStub 数据库引用
     * @return 有序的数据块
     */
    protected Block[] sort(final Block[] blocksStub) {
        // 下标索引
        final Block[] blocksMapping = new Block[blocksStub.length];
        System.arraycopy(blocksStub, 0, blocksMapping, 0,  blocksStub.length);
        for (Block block : blocksStub) {
            if (null == block.getTableNode().getPojoMapping() || !block.getTableNode().getPojoMapping().havingMappedToSource()) {
                continue;
            }
            Block toBeReplaced = block.getBelong();
            // 计算原数组需要挪动的块与本节点相距距离
            int range = Math.abs(toBeReplaced.getIndex() - block.getIndex());
            // [start --- end] 为 本节点起始位置 --- 本节点起始位置 + 本节点长度
            int start = block.getIndex();
            int end = block.getIndex() + block.getLength();
            // 将 [start --- end] 下标范围的数据整体向前移动 range个下标(待替换的块与本节点的相距距离) 以达到前移并覆盖效果
            this.changePosition(blocksMapping, start, end, - range);
            // [start --- end] 为 待替换节点起始位置 --- 待替换节点起始位置 + 与本节点相距距离
            start = toBeReplaced.getIndex();
            end = toBeReplaced.getIndex() + range;
            // 将 [start --- end] 下标范围的数据整体向后移动 本节点长度 个下标 这样即完成了两个节点的下标互换
            this.changePosition(blocksMapping, start, end, block.getLength());
            // 刷新下标索引
            this.refreshIndexMapping(blocksStub, blocksMapping);
        }
        return blocksMapping;
    }

    protected void changePosition(Block[] blocksMapping, int start, int end, int interval) {
        for (int i = start; i < end; i++) {
            Block block = blocksMapping[i];
            block.setIndex(block.getIndex() + interval);
        }
    }

    protected void refreshIndexMapping(Block[] blocksStub, Block[] blocksMapping) {
        for (Block block : blocksStub) {
            blocksMapping[block.getIndex()] = block;
        }
    }

    protected static class Block {
        private final TableNode tableNode;
        private final Block belong;
        private int index;
        private int length;

        public Block(TableNode tableNode, Block belong, int index) {
            this.tableNode = tableNode;
            this.belong = belong;
            this.index = index;
        }

        public TableNode getTableNode() {
            return tableNode;
        }

        public Block getBelong() {
            return belong;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        @Override
        public String toString() {
            String belongSuffix = belong == null ? "0" : String.valueOf(belong.getIndex());
            return this.tableNode.getPath().getFullPath() + "@" + length + " -- " + index + ":" + belongSuffix;
        }
    }

    protected static class Counter {
        private int val;

        public Counter() {
        }

        public int getVal() {
            return val;
        }

        public void increment() {
            this.increment(1);
        }

        public void increment(int val) {
            this.val += val;
        }
    }
}
