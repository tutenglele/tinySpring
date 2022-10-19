package cn.tinyspring.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.PropertyValues;
import cn.tinyspring.springframework.beans.factory.BeanFactory;
import cn.tinyspring.springframework.beans.factory.BeanFactoryAware;
import cn.tinyspring.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.tinyspring.springframework.beans.factory.config.ConfigurableBeanFactory;
import cn.tinyspring.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.tinyspring.springframework.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * 实现接口 InstantiationAwareBeanPostProcessor 的一个用于在 Bean 对象实例化完成后，
 * 设置属性操作前的处理属性信息的类和操作方法
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 处理类含有 @Value、@Autowired 注解的属性，进行属性信息的提取和设置
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        //获得该类的自定义字段。
        Field[] declaredFields = clazz.getDeclaredFields();
        //处理注解@Value
        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddingValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 2.处理注解@Autowired
        // spring @Qualifier 搭配 @Autowired使用
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (null != qualifierAnnotation) {
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return pvs;
    }

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessorBeforeInstantiation(Class<?> bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessorAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }
}
