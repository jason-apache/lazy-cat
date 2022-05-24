package cool.lazy.cat.orm.api.manager.provider;

import cool.lazy.cat.orm.api.base.anno.ApiPojo;
import cool.lazy.cat.orm.api.base.anno.ApiQueryFilter;
import cool.lazy.cat.orm.api.base.anno.Entry;
import cool.lazy.cat.orm.api.base.constant.HttpMethod;
import cool.lazy.cat.orm.api.exception.ExistNameSpaceException;
import cool.lazy.cat.orm.api.exception.ExistPathApiException;
import cool.lazy.cat.orm.api.manager.subject.ApiPojoSubject;
import cool.lazy.cat.orm.api.manager.subject.ApiQueryFilterInfo;
import cool.lazy.cat.orm.api.util.PathGenerator;
import cool.lazy.cat.orm.api.web.EntryInfo;
import cool.lazy.cat.orm.api.web.EntryInfoImpl;
import cool.lazy.cat.orm.api.web.entrust.method.ApiMethodEntry;
import cool.lazy.cat.orm.base.util.Caster;
import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.base.util.StringUtil;
import cool.lazy.cat.orm.core.jdbc.mapping.field.attr.PojoField;
import cool.lazy.cat.orm.core.manager.PojoManager;
import cool.lazy.cat.orm.core.manager.PojoTableManager;
import cool.lazy.cat.orm.core.manager.subject.PojoSubject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2022-05-23 22:55
 * 根据元注解生成api映射
 * @see cool.lazy.cat.orm.api.base.anno
 */
public class MetaAnnotationApiPojoSubjectProvider implements ApiPojoSubjectProvider {

    protected final PojoManager pojoManager;
    protected final PojoTableManager pojoTableManager;

    public MetaAnnotationApiPojoSubjectProvider(PojoManager pojoManager, PojoTableManager pojoTableManager) {
        this.pojoManager = pojoManager;
        this.pojoTableManager = pojoTableManager;
    }

    @Override
    public List<ApiPojoSubject> provider() {
        List<PojoSubject> subjectList = pojoManager.getPojoSubjectList().stream().filter(p -> p.getPojoType().getAnnotation(ApiPojo.class) != null).collect(Collectors.toList());
        List<ApiPojoSubject> result = new ArrayList<>(subjectList.size());
        Set<String> nameSpaces = new HashSet<>(subjectList.size());
        for (PojoSubject subject : subjectList) {
            ApiPojoSubject apiPojoSubject = new ApiPojoSubject(subject.getPojoType());
            //初始化命名空间、api方法
            this.initApiPojoSubject(apiPojoSubject);
            if (nameSpaces.contains(apiPojoSubject.getNameSpace())) {
                throw new ExistNameSpaceException("已存在的nameSpace：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + apiPojoSubject.getNameSpace()+ "]");
            }
            // 初始化字段查询条件
            this.initQueryFilter(apiPojoSubject, pojoTableManager.getByPojoType(subject.getPojoType()).getTableInfo().getFieldInfoMap());
            nameSpaces.add(apiPojoSubject.getNameSpace());
            result.add(apiPojoSubject);
        }
        return result;
    }

    protected void initApiPojoSubject(ApiPojoSubject apiPojoSubject) {
        ApiPojo apiPojoAnnotation = apiPojoSubject.getPojoType().getAnnotation(ApiPojo.class);
        if (null != apiPojoAnnotation) {
            Map<String, Map<HttpMethod, EntryInfo>> entryInfoMap = new HashMap<>(apiPojoAnnotation.entry().length);
            List<EntryInfo> entryInfoList = new ArrayList<>(apiPojoAnnotation.entry().length);
            // 初始化命名空间
            String nameSpace = PathGenerator.format(apiPojoAnnotation.nameSpace());
            if (StringUtil.isBlank(nameSpace)) {
                String name = apiPojoSubject.getPojoType().getSimpleName();
                // 转小驼峰
                nameSpace = PathGenerator.format(StringUtil.upper2Lower(name));
            }
            // 分析api
            for (Entry entry : apiPojoAnnotation.entry()) {
                if (entry.api().isInterface()) {
                    throw new IllegalArgumentException("不是一个ApiMethodEntry实现类：" + entry.api().getName());
                }
                if (!ApiMethodEntry.class.isAssignableFrom(entry.api())) {
                    throw new UnsupportedOperationException("不支持类型, 请自定义实现: " + entry.api());
                }
                if (CollectionUtil.isEmpty(entry.methods())) {
                    throw new IllegalArgumentException("method为空：" + entry.path());
                }
                // 生成完整路径
                String fullPath = nameSpace + PathGenerator.format(entry.path());
                EntryInfo entryMethod = new EntryInfoImpl(apiPojoSubject.getPojoType(), nameSpace, fullPath, Caster.cast(entry.api()), entry.methods());
                entryMethod.initParameter(entry.parameters());
                for (HttpMethod httpMethod : entryMethod.getMethods()) {
                    if (entryInfoMap.get(entryMethod.getFullPath()) != null && entryInfoMap.get(entryMethod.getFullPath()).containsKey(httpMethod)) {
                        throw new ExistPathApiException("已存在的path：代理类" + apiPojoSubject.getPojoType().getName() + "[value：" + entryMethod.getFullPath() + "，type：" + Arrays.toString(entry.methods()) + "]");
                    }
                    // 生成method映射结构
                    entryInfoMap.computeIfAbsent(entryMethod.getFullPath(), l -> new HashMap<>()).put(httpMethod, entryMethod);
                }
                entryInfoList.add(entryMethod);
            }
            apiPojoSubject.setEntryInfoList(entryInfoList);
            apiPojoSubject.setNameSpace(nameSpace);
            apiPojoSubject.setEntryInfoMap(entryInfoMap);
            apiPojoSubject.initParameter(apiPojoAnnotation.parameters());
        }
    }

    /**
     * 初始字段化查询条件
     * @param apiPojoSubject apiPojo主体
     * @param fieldInfoMap pojo字段映射
     */
    protected void initQueryFilter(ApiPojoSubject apiPojoSubject, Map<String, PojoField> fieldInfoMap) {
        Map<String, ApiQueryFilterInfo> apiQueryFilterInfoMap = new HashMap<>(fieldInfoMap.size());
        for (Map.Entry<String, PojoField> entry : fieldInfoMap.entrySet()) {
            String key = entry.getKey();
            ApiQueryFilter apiQueryFilter = entry.getValue().getGetter().getAnnotation(ApiQueryFilter.class);
            if (null == apiQueryFilter) {
                continue;
            }
            apiQueryFilterInfoMap.put(key, new ApiQueryFilterInfo(apiQueryFilter));
        }
        if (!apiQueryFilterInfoMap.isEmpty()) {
            apiPojoSubject.setQueryFilterInfoMap(apiQueryFilterInfoMap);
        }
    }
}
