package by.grsu.iot.service.validation.factory;

import by.grsu.iot.service.validation.validator.ApplicationValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;

import java.util.List;

@Component
public class DataBinderFactory {

    private static List<? extends ApplicationValidator> validators;

    public DataBinderFactory(List<? extends ApplicationValidator> validators) {
        DataBinderFactory.validators = validators;
    }

    public static <T> DataBinder createDataBinder(T t) throws IllegalArgumentException {
        DataBinder dataBinder = new DataBinder(t);

        validators.forEach(validator -> {
            if (validator.supports(t.getClass())) {
                dataBinder.setValidator(validator);
            }
        });

        if (dataBinder.getValidators().size() == 0) {
            throw new IllegalArgumentException("Not found " + ApplicationValidator.class
                    + " for such class " + t.getClass());
        }

        return dataBinder;
    }
}
