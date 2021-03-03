package by.grsu.iot.service.validation.factory;

import by.grsu.iot.service.validation.validator.ApplicationValidator;
import org.springframework.validation.DataBinder;

/**
 * Factory for {@link DataBinder}
 *
 * @author Ilya Ilyukou
 * @see DataBinder
 */
public interface DataBinderFactory {

    /**
     * Create a {@link DataBinder} and with set {@link ApplicationValidator}
     * If factory not contains {@link ApplicationValidator} for argument, than
     * throw {@link IllegalArgumentException}
     *
     * @param t   any object
     * @param <T> any object
     * @return {@link DataBinder} for validate argument.
     */
    <T> DataBinder createDataBinder(T t) throws IllegalArgumentException;
}
