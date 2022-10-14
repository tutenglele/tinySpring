package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
/**
 * 实现bean的实例化，暂时只能解决构造函数无参的情况下
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //一旦bean初始化，就加载进容器里面，方便下次使用
        addSingleton(beanName, bean);
        return bean;
    }
}
