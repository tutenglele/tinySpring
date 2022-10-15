package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.core.io.DefaultResourceLoader;
import cn.tinyspring.springframework.core.io.ResourceLoader;

/**
 * 抽象bean定义读类，负责获取bean注册和资源加载，这样子类只负责将两者结合就行了
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
    private final BeanDefinitionRegistry registry;
    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
