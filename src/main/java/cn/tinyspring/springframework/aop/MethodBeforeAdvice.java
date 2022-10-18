package cn.tinyspring.springframework.aop;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice{
    /**
     * Callback before a given method is invoked.
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method,Object[] args, Object target) throws Throwable;
}
