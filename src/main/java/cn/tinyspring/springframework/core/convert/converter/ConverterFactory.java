package cn.tinyspring.springframework.core.convert.converter;

/**
 * 类型转换工厂
 * @param <S>
 * @param <R>
 */
public interface ConverterFactory<S, R> {
    /**
     * 获得一个类型转换器
     * @param targetType
     * @param <T>
     * @return
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
