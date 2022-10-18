package cn.tinyspring.springframework.aop.aspectj;

import cn.tinyspring.springframework.aop.Pointcut;
import cn.tinyspring.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 具体的拦截方法
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;
    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut == null ? new AspectJExpressionPointcut(expression) : pointcut;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
