package cool.lazy.cat.orm.core.manager.subject;

import cool.lazy.cat.orm.base.constant.Constant;
import cool.lazy.cat.orm.core.manager.exception.CannotResolverBeanInfoException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: mahao
 * @date: 2021/3/4 20:37
 */
public class PojoSubject implements Subject {

    protected Class<?> pojoType;
    protected Set<MethodDescriptor> setters;
    protected Set<MethodDescriptor> getters;

    public void init() {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(pojoType);
            this.setters = Arrays.stream(beanInfo.getMethodDescriptors()).filter(m -> m.getMethod().getName().startsWith(Constant.SET_METHOD_PREFIX)).collect(Collectors.toSet());
            this.getters = Arrays.stream(beanInfo.getMethodDescriptors()).filter(m -> m.getMethod().getName().startsWith(Constant.GET_METHOD_PREFIX)).collect(Collectors.toSet());
        } catch (IntrospectionException e) {
            throw new CannotResolverBeanInfoException("无法解析bean对象信息，请检查：" + pojoType.getName(), e);
        }
    }

    public void setPojoType(Class<?> pojoType) {
        this.pojoType = pojoType;
    }

    public Class<?> getPojoType() {
        return pojoType;
    }

    public Set<MethodDescriptor> getSetters() {
        return setters;
    }

    public void setSetters(Set<MethodDescriptor> setters) {
        this.setters = setters;
    }

    public Set<MethodDescriptor> getGetters() {
        return getters;
    }

    public void setGetters(Set<MethodDescriptor> getters) {
        this.getters = getters;
    }
}
