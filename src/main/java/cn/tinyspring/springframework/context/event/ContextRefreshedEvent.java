package cn.tinyspring.springframework.context.event;

import cn.tinyspring.springframework.context.ApplicationEvent;

/**
 * 刷新事件类
 */
public class ContextRefreshedEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
