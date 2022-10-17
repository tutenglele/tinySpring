package cn.tinyspring.springframework.beans.factory;

public interface InitializingBean {
    /**
     * bean填充属性后调用该方法
     */
    void afterPropertiesSet() throws Exception;
}
