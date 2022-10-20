package cn.tinyspring.springframework.beans.factory.support;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.DisposableBean;
import cn.tinyspring.springframework.beans.factory.ObjectFactory;
import cn.tinyspring.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现接口SingletonBeanRegistry
 * 并实现了一个受保护的 addSingleton 方法，这个方法可以被继承此类的其他类调用
 */
public class DefaultSingletonBeanRegistory implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    /**
     * 一级缓存普通对象
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存半成品对象，提前暴漏没有完全实例化的对象，原始对象还没用进行属性注入和后续的BeanPostProcessor等生命周期
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 缓存ObjectFactory，用来生成原始对象aop得到的代理对象。在每个Bean生成过程中，都会提前暴漏一个工厂，出现循环依赖用到这个工厂
     * 假如A依赖B，A执行ObjectFactory提交得到一个aop之后的代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    /**
     * 通过一层层处理，最终在三级缓存中拿到对应的对象
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (null == singletonObject) {
            singletonObject = earlySingletonObjects.get(beanName);
            // 判断二级缓存中是否有对象，这个对象就是代理对象，因为只有代理对象才会放到三级缓存中
            if (null == singletonObject) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    /**
     * 将bean加载入最终的一级容器中，清除其余二级容器
     * @param beanName
     * @param singletonObject
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 销毁方法保存
     * @param beanName
     * @param disposableBean
     */
    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    public void destorySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        for(int i = disposableBeanNames.length-1; i>=0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destory();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
