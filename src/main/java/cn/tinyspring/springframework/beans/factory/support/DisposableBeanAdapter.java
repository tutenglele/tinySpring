package cn.tinyspring.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.DisposableBean;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 考虑到有两种进行销毁方法：实现接口，配置文件反射，采用适配器模型进行统一
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final String beanName;
    private final Object bean;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.bean = bean;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destory() throws Exception {
        // 1. 实现接口 DisposableBean
        if(bean instanceof DisposableBean) {
            ((DisposableBean) bean).destory();
        }
        // 2. 配置信息 destroy-method {判断是为了避免二次执行销毁}
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destory".equals(this.destroyMethodName))) {
            Method destoryMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destoryMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destoryMethod.invoke(bean);
        }
    }
}
