package cn.tinyspring.springframework.beans.factory.config;

import cn.tinyspring.springframework.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessorBeforeInstantiation(Class<?> bean, String beanName) throws BeansException;
}
