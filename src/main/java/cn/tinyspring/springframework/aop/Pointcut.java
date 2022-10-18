package cn.tinyspring.springframework.aop;

/**
 * 切入点接口
 */
public interface Pointcut {
    /**
     * 返回类过滤器
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * 返回方法匹配器
     * @return
     */
    MethodMatcher getMethodMatcher();
}
