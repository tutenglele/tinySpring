package cn.tinyspring.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 定义访问者
 */
public interface Advisor {
    Advice getAdvice();
}
