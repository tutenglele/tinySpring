package cn.tinyspring.springframework.core.convert.support;

import cn.tinyspring.springframework.core.convert.converter.ConverterRegistry;

/**
 * 默认类型转换服务
 */
public class DefaultConversionService extends GenericConversionService {
    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        //添加各种类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }
}
