package by.grsu.iot.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is used to mark fields that require validation.
 * <p>
 * If annotation present on {@link java.util.Collection} that validate a collection element
 *
 * @author Ilyukou Ilya
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValidation {

    /**
     * @return size the element must be higher or equal to
     */
    int min() default 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max() default Integer.MAX_VALUE;

    /**
     * @return are spaces allowed in the string
     */
    boolean spaceAllowed() default false;
}
