package cn.tinyspring.springframework.context;

import java.util.EventObject;

/**
 * 具备事件功能的抽象类，所有的事件包括关闭、刷新，以及用户自己实现的事件，都需要继承这个类
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
