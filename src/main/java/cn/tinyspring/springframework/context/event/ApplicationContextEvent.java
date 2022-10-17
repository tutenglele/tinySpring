package cn.tinyspring.springframework.context.event;

import cn.tinyspring.springframework.context.ApplicationContext;
import cn.tinyspring.springframework.context.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
