package cn.tinyspring.springframework.beans.factory;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.PropertyValue;
import cn.tinyspring.springframework.beans.PropertyValues;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
import cn.tinyspring.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.tinyspring.springframework.core.io.DefaultResourceLoader;
import cn.tinyspring.springframework.core.io.Resource;
import cn.tinyspring.springframework.utils.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 处理占位符配置，使用到 BeanFactoryPostProcessor 在所有的 BeanDefinition 加载完成后，
 * 实例化 Bean 对象之前，修改 BeanDefinition 的属性信息。为后续处理关于占位符配置到注解上做准备
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private static final String DEFAULT_PLACEHOLDER_PREEIX = "${";

    private static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            //完成对配置文件的加载
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) {
                        continue;
                    }

                    //获取占位符位置
                    value = resolverPlaceholder((String) value, properties);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
                }
            }
            //向容器中添加字符串解析器，解析Value注解使用
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            //把属性值写入到了 AbstractBeanFactory 的 embeddedValueResolvers 中
            beanFactory.addEmbeddedValueResolver(valueResolver);
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String resolverPlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder builder = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREEIX);
        int endIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if(startIdx != -1 && endIdx != -1 && startIdx < endIdx) {
            String propKey = strVal.substring(startIdx + 2, endIdx);
            String propVal = properties.getProperty(propKey);
            builder.replace(startIdx, endIdx + 1, propVal);
        }
        return builder.toString();
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolverStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolverPlaceholder(strVal, properties);
        }
    }
}
