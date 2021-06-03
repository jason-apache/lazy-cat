package com.lazy.cat.orm.core.manager.scan;

import com.lazy.cat.orm.core.base.annotation.Pojo;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2021/3/4 13:26
 */
public class ClassPathPojoScanner extends ClassPathBeanDefinitionScanner {

    private ResourcePatternResolver resourcePatternResolver;
    /**
     * 扫描全部
     */
    private String resourcePattern = "**/*.class";

    public ClassPathPojoScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public List<Class<?>> doScanPojo(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        List<Class<?>> pojoClasses = new ArrayList<>();
        for (String basePackage : basePackages) {
            pojoClasses.addAll(this.analysisPojo(basePackage));
        }
        return pojoClasses;
    }

    private List<Class<?>> analysisPojo(String basePackage) {
        // 扫描路径
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + '/' + this.resourcePattern;
        List<Class<?>> beanClass = new ArrayList<>();
        try {
            Resource[] resources = this.getResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                    try {
                        Class<?> pojoClass = Class.forName(sbd.getMetadata().getClassName());
                        // 只注册Pojo注解标注的类
                        if (pojoClass.getAnnotation(Pojo.class) != null) {
                            beanClass.add(pojoClass);
                        }
                    } catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(sbd.getMetadata().getClassName());
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return beanClass;
    }

    private ResourcePatternResolver getResourcePatternResolver() {
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return this.resourcePatternResolver;
    }
}
