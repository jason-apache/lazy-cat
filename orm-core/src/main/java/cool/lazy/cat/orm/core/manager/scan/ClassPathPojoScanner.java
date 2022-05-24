package cool.lazy.cat.orm.core.manager.scan;

import cool.lazy.cat.orm.annotation.Pojo;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/4 13:26
 */
public class ClassPathPojoScanner extends ClassPathBeanDefinitionScanner {

    /**
     * 扫描全部
     */
    final String resourcePattern = "*.class";

    public ClassPathPojoScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public List<Class<?>> doScanPojo(List<String> basePackages, List<String> excludePackages) {
        List<Class<?>> pojoClasses = new ArrayList<>();
        for (String basePackage : basePackages) {
            pojoClasses.addAll(this.analysisPojo(basePackage, excludePackages));
        }
        return pojoClasses.stream().distinct().collect(Collectors.toList());
    }

    private List<Class<?>> analysisPojo(String basePackage, List<String> excludePackages) {
        // 扫描路径
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + super.resolveBasePackage(basePackage) + '/' + this.resourcePattern;
        List<Class<?>> beanClass = new ArrayList<>();
        PathMatcher pathMatcher = new AntPathMatcher();
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = patternResolver.getResources(packageSearchPath);
            outer: for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                    metadataReader.getAnnotationMetadata().getClassName();
                    try {
                        Class<?> pojoClass = Class.forName(metadataReader.getAnnotationMetadata().getClassName());
                        String pkg = pojoClass.getPackage().getName();
                        for (String excludePackage : excludePackages) {
                            if (pathMatcher.match(excludePackage, pkg) || pkg.equals(excludePackage)) {
                                continue outer;
                            }
                        }
                        // 只注册Pojo注解标注的类
                        if (pojoClass.getAnnotation(Pojo.class) != null) {
                            beanClass.add(pojoClass);
                        }
                    } catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(metadataReader.getAnnotationMetadata().getClassName());
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return beanClass;
    }
}
