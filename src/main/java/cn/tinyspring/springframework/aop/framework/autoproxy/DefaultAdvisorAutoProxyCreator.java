package cn.tinyspring.springframework.aop.framework.autoproxy;

import cn.tinyspring.springframework.aop.*;
import cn.tinyspring.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.tinyspring.springframework.aop.framework.ProxyFactory;
import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.PropertyValues;
import cn.tinyspring.springframework.beans.factory.BeanFactory;
import cn.tinyspring.springframework.beans.factory.BeanFactoryAware;
import cn.tinyspring.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.tinyspring.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 融入Bean生命周期的自动代理创建者
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 用于记录原始对象是否已经代理
     */
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessorBeforeInstantiation(Class<?> bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessorAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    /**
     * 判断是不是aop的基础配置类
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        //如果是相关aop配套类就直接返回不进行切面了
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            //过滤匹配类
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(bean);

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);

            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }
    /**
     * 方便把依赖的切面对象也放到三级缓存中，生成代理对象并记录
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}
