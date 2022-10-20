package cn.tinyspring.springframework.test.dependence;

import cn.tinyspring.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class HusbandMother implements FactoryBean<IMother> {

    @Override
    public IMother getObject() throws Exception {
        return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IMother.class}, (proxy, method, args) -> "mama进行了代理" + method.getName());
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
