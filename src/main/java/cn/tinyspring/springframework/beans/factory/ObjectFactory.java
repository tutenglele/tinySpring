package cn.tinyspring.springframework.beans.factory;

import cn.tinyspring.springframework.beans.BeansException;

/**
 * 一个工厂对象接口
 * @param <T>
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
