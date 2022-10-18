package cn.tinyspring.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配，找到表达式范围内匹配下的目标类和方法。
 */
public interface MethodMatcher {
    /**
     * 判断类和方法是否匹配
     * @param method
     * @param clazz
     * @return
     */
    boolean matches(Method method, Class<?> clazz);
}
