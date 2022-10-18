package cn.tinyspring.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import cn.tinyspring.springframework.beans.factory.config.BeanDefinition;
import cn.tinyspring.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 扫描路径得到component
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 通过配置路径 basePackage=cn.bugstack.springframework.test.bean，解析出 classes 信息的工具方法
     * 通过这个方法就可以扫描到所有 @Component 注解的 Bean 对象
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}
