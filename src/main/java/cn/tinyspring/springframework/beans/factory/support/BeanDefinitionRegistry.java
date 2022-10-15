package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

/**
 * bean定义注册接口
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册bean定义
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断当前的bean定义是否已经存在
     * @param beanName
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回注册表中所有的bean名称
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 使用Bean名称查询BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
