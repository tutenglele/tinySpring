package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略接口.
 * 实例化有参数的bean，有两种策略，这里采用策略模式
 * 一个是基于 Java 本身自带的方法 DeclaredConstructor，另外一个是使用 Cglib 来动态创建 Bean 对象。
 */
public interface InstantiationStrategy {
    /**
     *
     * @param beanDefinition
     * @param beanName
     * @param ctor  Constructor 类，里面包含了一些必要的类信息，有这个参数的目的就是为了拿到符合入参信息相对应的构造函数
     * @param args 具体的入参信息
     * @return
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args);
}
