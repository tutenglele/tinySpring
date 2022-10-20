package cn.tinyspring.springframework.test.dependence;

import cn.tinyspring.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class SpouseAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("进行关怀" + method);
    }
}
