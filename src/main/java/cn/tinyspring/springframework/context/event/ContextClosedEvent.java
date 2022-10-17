package cn.tinyspring.springframework.context.event;

import cn.tinyspring.springframework.context.ApplicationEvent;

/**
 * 关闭事件类
 */
public class ContextClosedEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
