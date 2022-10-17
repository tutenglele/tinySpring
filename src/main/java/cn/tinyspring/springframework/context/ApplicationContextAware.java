package cn.tinyspring.springframework.context;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.Aware;

/**
 *  ApplicationContext 并不是在 AbstractAutowireCapableBeanFactory 中 createBean 方法下的内容，
 *  所以需要像容器中注册 addBeanPostProcessor ，
 *  再由 createBean 统一调用 applyBeanPostProcessorsBeforeInitialization 时进行操作
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext context) throws BeansException;
}
