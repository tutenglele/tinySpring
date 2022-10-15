package cn.tinyspring.springframework.beans.factory.config;

/**
 * 类引用，该类存储beanName，方便获得对应bean，
 */
public class BeanReference {
    private String name;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBeanName(String name) {
        name = name;
    }
}
