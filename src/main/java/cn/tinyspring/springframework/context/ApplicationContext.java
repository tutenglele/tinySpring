package cn.tinyspring.springframework.context;

import cn.tinyspring.springframework.beans.factory.HierarchicalBeanFactory;
import cn.tinyspring.springframework.beans.factory.ListableBeanFactory;
import cn.tinyspring.springframework.core.io.ResourceLoader;

/**
 * ApplicationContext 本身是 Central 接口，但目前还不需要添加一些获取ID和父类上下文，所以暂时没有接口方法的定义
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
