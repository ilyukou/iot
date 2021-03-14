package by.grsu.iot.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is used to mark fields that require validation
 *
 * @author Ilyukou Ilya
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {

    /**
     * @return size the element must be higher or equal to
     */
    int min() default 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max() default Integer.MAX_VALUE;

    /**
     * @return required field or not
     */
    boolean required() default true;

    /**
     * @return are spaces allowed in the string
     */
    boolean spaceAllowed() default false;
}
