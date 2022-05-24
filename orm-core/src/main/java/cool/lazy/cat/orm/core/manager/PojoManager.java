package cool.lazy.cat.orm.core.manager;


import cool.lazy.cat.orm.base.util.CollectionUtil;
import cool.lazy.cat.orm.core.manager.exception.UnKnowPojoException;
import cool.lazy.cat.orm.core.manager.scan.PojoClassScanner;
import cool.lazy.cat.orm.core.manager.subject.PojoSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/4 20:03
 * pojo托管
 */
public class PojoManager implements Manager<PojoSubject> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    protected final Map<Class<?>, PojoSubject> pojoSubjectMap;

    public PojoManager(List<String> scanBasePackages, List<String> excludePackages) {
        List<Class<?>> pojoClasses;
        if (CollectionUtil.isNotEmpty(scanBasePackages)) {
            LOGGER.info("使用配置文件声明的扫描路径加载pojo...");
            pojoClasses = PojoClassScanner.scan(scanBasePackages, excludePackages);
        } else {
            pojoClasses = PojoClassScanner.take();
        }
        if (null == pojoClasses) {
            throw new NullPointerException("无法加载pojo类信息");
        }
        pojoSubjectMap = new HashMap<>(pojoClasses.size());
        for (Class<?> pojoClass : pojoClasses) {
            PojoSubject subject = new PojoSubject();
            subject.setPojoType(pojoClass);
            subject.init();
            pojoSubjectMap.put(pojoClass, subject);
        }
        if (LOGGER.isDebugEnabled()) {
            String prefix = "\r\n -- \t";
            String output = prefix + pojoSubjectMap.values().stream().map(c -> c.getPojoType().getName()).collect(Collectors.joining(prefix));
            LOGGER.debug("加载pojo类信息: {}", output);
        }
    }

    public List<PojoSubject> getPojoSubjectList() {
        return new ArrayList<>(this.pojoSubjectMap.values());
    }

    /**
     * 根据pojo类型返回pojo信息
     * @param pojoType pojo类型
     * @return pojo主体信息
     */
    @Override
    public PojoSubject getByPojoType(Class<?> pojoType) {
        PojoSubject subject = pojoSubjectMap.get(pojoType);
        if (null == subject) {
            throw new UnKnowPojoException("未定义的pojo类型：" + pojoType.getName());
        }
        return subject;
    }
}
