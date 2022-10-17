package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 关于 FactoryBean 此类对象的注册操作
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistory{
    //定义了缓存操作 factoryBeanObjectCache，用于存放单例类型的对象，避免重复创建。
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object obj = this.factoryBeanObjectCache.get(beanName);
        return (obj != NULL_OBJECT ? obj : null);//con.Hashmap不支持存储空值
    }

    /**
     * 从缓存中拿取Object， 如果没有就从factoryBean拿，并存到缓存
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object obj = this.factoryBeanObjectCache.get(beanName);
            if (obj == null) {
                obj = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (obj != null ? obj : NULL_OBJECT));
            }
            return (obj != NULL_OBJECT ? obj : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
