package by.grsu.iot.service.validation.factory;

import by.grsu.iot.service.validation.validator.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.List;

@Service
public class DataBinderFactoryImpl implements DataBinderFactory {

    private final List<? extends ApplicationValidator> validators;

    public DataBinderFactoryImpl(List<? extends ApplicationValidator> validators) {
        this.validators = validators;
    }

    @Override
    public <T> DataBinder createDataBinder(T t) {
        DataBinder dataBinder = new DataBinder(t);

        validators.forEach(validator -> {
            if (validator.supports(t.getClass())){
                dataBinder.setValidator(validator);
            }
        });

        if (dataBinder.getValidators().size() == 0){
            throw new IllegalArgumentException("Not found " + Validator.class + " for such class " + t.getClass());
        }

        return dataBinder;
    }
}
