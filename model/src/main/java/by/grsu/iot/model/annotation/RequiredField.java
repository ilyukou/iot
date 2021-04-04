package by.grsu.iot.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is used to mark fields that require
 *
 * @author Ilyukou Ilya
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredField {

    /**
     * @return required field or not
     */
    boolean required() default true;
}
