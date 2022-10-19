package cn.tinyspring.springframework.beans.factory.config;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.PropertyValues;

/**
 * 继承BeanPostProcessor,在bean初始化下，执行方法
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessorBeforeInstantiation(Class<?> bean, String beanName) throws BeansException;

    /**
     * 在Bean对象实例化后执行该方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessorAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 在bean对象实例化完成后，设置属性操作之前执行方法
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
