package cn.tinyspring.springframework.aop;

/**
 * 定义类匹配类，用于切点找到给定的接口和目标类
 */
public interface ClassFilter {
    /**
     * 判断类或者接口是否匹配
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
