package cn.tinyspring.springframework.core.convert.converter;

/**
 * 类型转换接口
 * @param <S> 原类型
 * @param <T> 目标类型
 */
public interface Converter<S, T> {
    /**
     * 将S类型转换为T类型
     * @param source
     * @return
     */
    T convert(S source);
}
