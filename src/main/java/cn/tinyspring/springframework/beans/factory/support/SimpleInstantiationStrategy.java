package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 采用JDK实例化方式 clazz.getDeclaredConstructor(Parameter).newInstance(args)
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Class beanClass = beanDefinition.getBeanClass();

        try {
            if (null != ctor) {
                return beanClass.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return beanClass.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
