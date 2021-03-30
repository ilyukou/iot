package by.grsu.iot.api.validation;

import by.grsu.iot.model.annotation.CollectionValidation;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.service.util.EntityFieldValidationUtil;
import by.grsu.iot.service.util.ObjectUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

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

        if (ObjectUtil.hasClassAnnotatedField(body.getClass(), StringValidation.class)) {
            EntityFieldValidationUtil.validateString(body);
        }

        if (ObjectUtil.hasClassAnnotatedField(body.getClass(), CollectionValidation.class)) {
            EntityFieldValidationUtil.validateCollection(body);
        }

        return body;
    }
}
