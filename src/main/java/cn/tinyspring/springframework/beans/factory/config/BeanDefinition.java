package cn.tinyspring.springframework.beans.factory.config;

/**
 * 用于定义 Bean 实例化信息，现在的实现是以一个 Object 存放对象,改为Class对象，将对象的实例化交给容器
 */
public class BeanDefinition {
    private Class beanClass;
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
