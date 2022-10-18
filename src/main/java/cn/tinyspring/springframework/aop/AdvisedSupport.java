package cn.tinyspring.springframework.aop;


import org.aopalliance.intercept.MethodInterceptor;

/**
 * 切面通知信息，用于把代理、拦截、匹配的各项属性包装到一个类中，方便在 Proxy 实现类进行使用。
 */
public class AdvisedSupport {
    /**
     * 被代理的目标对象
     */
    private TargetSource targetSource;
    /**
     * 方法拦截器由用户自己实现 MethodInterceptor#invoke 方法，做具体的处理
     */
    private MethodInterceptor methodInterceptor;
    /**
     * 方法匹配器(检查目标方法是否符合通知条件)这个对象由 AspectJExpressionPointcut 提供服务
     */
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
