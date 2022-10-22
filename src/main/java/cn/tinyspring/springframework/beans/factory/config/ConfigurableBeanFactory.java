package cn.tinyspring.springframework.beans.factory.config;

import cn.tinyspring.springframework.beans.factory.HierarchicalBeanFactory;
import cn.tinyspring.springframework.core.convert.ConversionService;
import cn.tinyspring.springframework.utils.StringValueResolver;

/**
 * 获取 BeanPostProcessor、BeanClassLoader等的一个配置化接口
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destorySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddingValue(String value);

    void setConversionService(ConversionService conversionService);

    ConversionService getConversionService();
}
