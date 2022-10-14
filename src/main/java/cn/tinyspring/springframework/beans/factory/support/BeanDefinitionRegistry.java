package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

/**
 *
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册bean定义
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
