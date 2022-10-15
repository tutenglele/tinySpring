package cn.tinyspring.springframework.beans.factory;

import cn.tinyspring.springframework.beans.BeansException;

import java.util.Map;

/**
 * 一个扩展 Bean 工厂接口的接口
 * 新增加了 getBeansOfType、getBeanDefinitionNames() 方法
 */
public interface ListableBeanFactory extends BeanFactory{
    /**
     * 按照类型返回 Bean 实例
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回所有beanDefinition的名称
     */
    String[] getBeanDefinitionNames();
}
