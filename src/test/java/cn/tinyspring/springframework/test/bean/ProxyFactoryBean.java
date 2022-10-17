package cn.tinyspring.springframework.test.bean;

import cn.tinyspring.springframework.beans.BeansException;
import cn.tinyspring.springframework.beans.factory.FactoryBean;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyFactoryBean implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception{
        InvocationHandler handler = null;
        try {
            handler = (proxy, method, args)->{
                if ("toString".equals(method.getName())) return this.toString();
                Map<String, String> map = new HashMap<>();
                map.put("111", "sdfsdg");
                map.put("112", "fdgfro");
                return "你被代理了" + method.getName() + ":" + map.get(args[0].toString());
            };
        } catch (Exception e) {
            throw new BeansException("FactoryBean使用getObjcet方法失败");
        }

        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
