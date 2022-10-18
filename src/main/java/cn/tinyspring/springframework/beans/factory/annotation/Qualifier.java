package cn.tinyspring.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 用来限定注入的Bean的名称
 * @Inherited 被它修饰的Annotation将具有继承性
 * @Documented 将在javadoc文档里面
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.PARAMETER})
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
