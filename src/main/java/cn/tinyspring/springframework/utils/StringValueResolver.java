package cn.tinyspring.springframework.utils;

/**
 * 解析字符串接口
 */
public interface StringValueResolver {
    /**
     * 解析字符串
     * @param strVal
     * @return
     */
    String resolverStringValue(String strVal);
}
