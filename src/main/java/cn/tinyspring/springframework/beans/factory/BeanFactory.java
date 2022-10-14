package cn.tinyspring.springframework.beans.factory;

import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanFactory，代表了 Bean 对象的工厂，可以存放 Bean 定义到 Map 中以及获取
 *
 * 简单的 Spring Bean 容器实现，还需 Bean 的定义、注册、获取三个基本步骤，简化设计如下
 * 定义：BeanDefinition，可能这是你在查阅 Spring 源码时经常看到的一个类，例如它会包括 singleton、prototype、BeanClassName 等。但目前我们初步实现会更加简单的处理，只定义一个 Object 类型用于存放对象。
 * 注册：BeanFactory这个过程就相当于我们把数据存放到 HashMap 中，只不过现在 HashMap 存放的是定义了的 Bean 的对象信息。
 * 获取：最后就是获取对象，Bean 的名字就是key，Spring 容器初始化好 Bean 以后，就可以直接获取了。
 */
public interface BeanFactory {
    /**
     * 根据bean名称获取bean对象
     * @param name
     * @return
     */
    Object getBean(String name);

    /**
     * 根据名称和参数，实例化bean，在获取 Bean 时把构造函数的入参信息传递进去了
     * @param name
     * @param args
     * @return
     */
    Object getBean(String name, Object... args);
}
