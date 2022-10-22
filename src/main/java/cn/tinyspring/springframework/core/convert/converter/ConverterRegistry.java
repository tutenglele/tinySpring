package cn.tinyspring.springframework.core.convert.converter;

/**
 * 类型转换器注册接口
 */
public interface ConverterRegistry {
    /**
     * 添加类型转换器
     * @param converter
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 添加Generic类型转换器
     * @param converter
     */
    void addConverter(GenericConverter converter);

    /**
     * 添加类型转换器工厂
     * @param converterFactory
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);
}
