package by.grsu.iot.service.validation.util;

import by.grsu.iot.service.domain.form.Form;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.validation.factory.DataBinderFactory;
import org.springframework.validation.DataBinder;

public class DataBinderValidation {

    public static void validateParam(Form form) {
        DataBinder dataBinder = DataBinderFactory.createDataBinder(form);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ExceptionUtil.throwException(dataBinder.getBindingResult());
        }
    }
}
