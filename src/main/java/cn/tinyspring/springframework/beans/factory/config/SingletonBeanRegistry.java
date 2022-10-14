package cn.tinyspring.springframework.beans.factory.config;

/**
 * 定义了一个获取单例对象的接口
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);
}
