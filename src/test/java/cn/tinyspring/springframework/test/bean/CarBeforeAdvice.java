package cn.tinyspring.springframework.test.bean;

import cn.tinyspring.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class CarBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法" + method.getName());
    }
}
