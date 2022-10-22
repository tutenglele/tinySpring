package cn.tinyspring.springframework.core.convert;

/**
 * 类型转换服务接口
 */
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
