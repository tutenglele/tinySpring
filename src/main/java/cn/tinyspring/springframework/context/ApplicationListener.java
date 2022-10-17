package cn.tinyspring.springframework.context;

import java.util.EventListener;

/**
 * 监听器接口
 * @param <E>
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * 监听一个事件
     * @param event
     */
    void onApplicationEvent(E event);
}
