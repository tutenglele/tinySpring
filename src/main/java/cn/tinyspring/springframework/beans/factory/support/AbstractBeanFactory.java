package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.BeanFactory;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

/**
 * 采用模板设计模式
 * getBean 并没有自身的去实现这些方法，
 * 而是只定义了调用过程以及提供了抽象方法，由实现此抽象类的其他类做相应实现。
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistory implements BeanFactory {

    /**
     * 对单例 Bean 对象的获取,在获取不到时需要拿到 Bean 的定义做相应 Bean 实例化操作。
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) {
        Object bean = getSingleton(name);
        if(bean != null) {
            return bean;
        }
        //一开始bean不存在，实例化bean
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
