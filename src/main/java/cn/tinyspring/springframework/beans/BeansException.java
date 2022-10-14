package cn.tinyspring.springframework.beans;

public class BeansException extends RuntimeException{

    public BeansException(String info, Exception e) {
        super(info, e);
    }
    public BeansException() {
        super();
    }

    public BeansException(String info) {
        super(info);
    }
}
