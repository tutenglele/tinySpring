package cn.tinyspring.springframework.beans.factory.config;

import cn.tinyspring.springframework.beans.PropertyValues;

/**
 * 用于定义 Bean 实例化信息，现在的实现是以一个 Object 存放对象,改为Class对象，将对象的实例化交给容器
 * 为了把属性交给 Bean 定义，所以这里填充了 PropertyValues 属性，并修改构造函数
 */
public class BeanDefinition {
    private Class beanClass;
    private PropertyValues propertyValues;
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
