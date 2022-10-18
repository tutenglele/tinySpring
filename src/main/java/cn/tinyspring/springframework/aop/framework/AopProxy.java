package cn.tinyspring.springframework.aop.framework;

/**
 * 获取代理类，采用cglib和JDK
 */
public interface AopProxy {
    /**
     * 获取代理类
     * @return
     */
    Object getProxy();
}
