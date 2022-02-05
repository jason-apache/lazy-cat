package cool.lazy.cat.orm.core.manager.scan;

import cool.lazy.cat.orm.core.manager.scan.annotation.PojoScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 13:23
 */
public class PojoClassScanner implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private static ClassPathPojoScanner scanner;
    private static List<Class<?>> payload = new ArrayList<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(PojoScan.class.getName()));
        // 创建类路径扫描器
        scanner = new ClassPathPojoScanner(registry);
        scanner.setResourceLoader(resourceLoader);
        List<String> basePackages = new ArrayList<>();
        for (String pkg : annoAttrs.getStringArray("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        payload = scanner.doScanPojo(basePackages);
    }

    public static List<Class<?>> scan(List<String> basePackages) {
        try {
            if (null == scanner) {
                return null;
            }
            return scanner.doScanPojo(basePackages);
        } finally {
            payload = null;
            scanner = null;
        }
    }

    public static List<Class<?>> take() {
        try {
            return payload;
        } finally {
            payload = null;
            scanner = null;
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
