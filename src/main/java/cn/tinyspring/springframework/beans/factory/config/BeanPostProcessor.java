package cn.tinyspring.springframework.beans.factory.config;

import cn.tinyspring.springframework.beans.BeansException;

/**
 * 提供了修改新实例化 Bean 对象的扩展点。
 */
public interface BeanPostProcessor {
    /**
     * 在bean对象初始化前执行该方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean对象执行初始化后执行该方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException;
}
