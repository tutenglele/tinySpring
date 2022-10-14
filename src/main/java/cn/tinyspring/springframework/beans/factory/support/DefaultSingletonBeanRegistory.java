package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现接口SingletonBeanRegistry
 * 并实现了一个受保护的 addSingleton 方法，这个方法可以被继承此类的其他类调用
 */
public class DefaultSingletonBeanRegistory implements SingletonBeanRegistry {
    private Map<String, Object> singletonObjects = new HashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 将bean加载入容器中
     * @param beanName
     * @param singletonObject
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
