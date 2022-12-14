package cn.tinyspring.springframework.context.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.tinyspring.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.tinyspring.springframework.beans.factory.config.BeanPostProcessor;
import cn.tinyspring.springframework.context.ApplicationEvent;
import cn.tinyspring.springframework.context.ApplicationListener;
import cn.tinyspring.springframework.context.ConfigurableApplicationContext;
import cn.tinyspring.springframework.context.event.ApplicationEventMulticaster;
import cn.tinyspring.springframework.context.event.ContextClosedEvent;
import cn.tinyspring.springframework.context.event.ContextRefreshedEvent;
import cn.tinyspring.springframework.context.event.SimpleApplicationEventMulticaster;
import cn.tinyspring.springframework.core.io.DefaultResourceLoader;

import java.beans.Beans;
import java.util.Collection;
import java.util.Map;

/**
 * 采用模板设计方式
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * refresh() 方法就是整个 Spring 容器的操作过程
     * 1创建 BeanFactory，并加载 BeanDefinition
     * 2获取 BeanFactory
     * 3在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
     * 4BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
     * 5提前实例化单例Bean对象
     * **新增关于 addBeanPostProcessor 添加 ApplicationContextAwareProcessor
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();
        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);
        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);
        // 6. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
        // 7. 初始化事件发布者
        initApplicationEventMulticaster();
        // 8.注册事件监听器
        registerListeners();
        // 9.发布容器刷新完成事件
        finishRefresh();
    }
    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    /**
     * 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
     * @param beanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册所有的BeanPostProcessors
     * @param beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }
    /**
    关于注册钩子和关闭的方法实现
     */
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
        // 执行销毁单例bean的销毁方法
        getBeanFactory().destorySingletons();
    }

    /**
     * 发布事件
     * @param event
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
