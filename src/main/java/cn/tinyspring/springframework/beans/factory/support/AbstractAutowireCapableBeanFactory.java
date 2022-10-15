package cn.tinyspring.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.PropertyValue;
import cn.tinyspring.springframework.beans.PropertyValues;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
import cn.tinyspring.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * 实现bean的实例化，暂时只能解决构造函数无参的情况下,解决有参数：1.如何把有参数传递进容器，2.如何实例化有参数的bean
 * 进一步扩展类的属性填充applyPropertyValues，暂时没有处理循环依赖问题
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            //bean实例创造出来后填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //一旦bean初始化，就加载进容器里面，方便下次使用
        addSingleton(beanName, bean);
        return bean;
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();
            if(value instanceof BeanReference) {
                //A对象依赖B对象，先获取B对象的实例化
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> ctor : declaredConstructors) {
            //根据参数长度，获得对应的构造器
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
