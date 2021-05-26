package by.grsu.iot.api.validation;

import by.grsu.iot.api.model.annotation.CollectionValidation;
import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.util.EntityFieldValidationUtil;
import by.grsu.iot.api.util.ObjectUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Controller advice that a validate all dto body after body read
 *
 * @author Ilyukou Ilya
 */
@ControllerAdvice
public class ValidationRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        if (body instanceof Collection) {
            Collection<?> collection = (Collection<?>) body;
            collection.forEach(this::validate);
        } else {
            validate(body);
        }
        return body;
    }

    private void validate(Object body) {
        if (ObjectUtil.hasClassAnnotatedField(body.getClass(), RequiredField.class)) {
            EntityFieldValidationUtil.checkRequiredField(body);
        }
        if (ObjectUtil.hasClassAnnotatedField(body.getClass(), StringValidation.class)) {
            EntityFieldValidationUtil.validateString(body);
        }
        if (ObjectUtil.hasClassAnnotatedField(body.getClass(), CollectionValidation.class)) {
            EntityFieldValidationUtil.validateCollection(body);
        }
    }
}
