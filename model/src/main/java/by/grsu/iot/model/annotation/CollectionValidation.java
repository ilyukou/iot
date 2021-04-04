package by.grsu.iot.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is used to mark {@link java.util.Collection} fields that require validation
 *
 * @author Ilyukou Ilya
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionValidation {

    /**
     * @return collection size must be higher or equal to
     */
    int minSize() default 0;

    /**
     * @return collection size must be lower or equal to
     */
    int maxSize() default Integer.MAX_VALUE;
}
