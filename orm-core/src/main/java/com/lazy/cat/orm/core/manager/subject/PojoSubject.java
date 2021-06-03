package com.lazy.cat.orm.core.manager.subject;

import com.lazy.cat.orm.core.base.constant.Constant;
import com.lazy.cat.orm.core.manager.exception.CannotResolverBeanInfoException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/4 20:37
 */
public class PojoSubject implements Subject {

    protected String pojoName;
    protected Class<?> pojoType;
    protected List<MethodDescriptor> setterList;
    protected List<MethodDescriptor> getterList;

    public void setPojoName(String pojoName) {
        this.pojoName = pojoName;
    }

    public void setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(pojoType);
            this.setterList = Arrays.stream(beanInfo.getMethodDescriptors()).filter(m -> m.getMethod().getName().startsWith(Constant.SET_METHOD_PREFIX)).collect(Collectors.toList());
            this.getterList = Arrays.stream(beanInfo.getMethodDescriptors()).filter(m -> m.getMethod().getName().startsWith(Constant.GET_METHOD_PREFIX)).collect(Collectors.toList());
        } catch (IntrospectionException e) {
            throw new CannotResolverBeanInfoException("无法解析bean对象信息，请检查：" + pojoType.getName());
        }
    }

    public String getPojoName() {
        return pojoName;
    }

    public Class<?> getPojoType() {
        return pojoType;
    }

    public List<MethodDescriptor> getSetterList() {
        return setterList;
    }

    public void setSetterList(List<MethodDescriptor> setterList) {
        this.setterList = setterList;
    }

    public List<MethodDescriptor> getGetterList() {
        return getterList;
    }

    public void setGetterList(List<MethodDescriptor> getterList) {
        this.getterList = getterList;
    }
}
