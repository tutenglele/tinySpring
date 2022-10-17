package cn.tinyspring.springframework.context.event;

import cn.tinyspring.springframework.context.ApplicationEvent;
import cn.tinyspring.springframework.context.ApplicationListener;

/**
 * 事件广播器
 * 定义了添加监听和删除监听的方法以及一个广播事件的方法 multicastEvent
 * 最终推送时间消息也会经过这个接口方法来处理谁该接收事件。
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加监听器
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除监听
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
