package by.grsu.iot.api.validation;

import by.grsu.iot.model.annotation.CollectionValidation;
import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.service.util.EntityFieldValidationUtil;
import by.grsu.iot.service.util.ObjectUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

import static io.vavr.API.*;

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

        Match(body).of(
                Case($(b -> ObjectUtil.hasClassAnnotatedField(b.getClass(), RequiredField.class)), run(() ->
                        EntityFieldValidationUtil.checkRequiredField(body)
                )),
                Case($(b -> ObjectUtil.hasClassAnnotatedField(b.getClass(), StringValidation.class)), run(() ->
                        EntityFieldValidationUtil.validateString(body)
                )),
                Case($(b -> ObjectUtil.hasClassAnnotatedField(b.getClass(), CollectionValidation.class)), run(() ->
                        EntityFieldValidationUtil.validateCollection(body)
                )));

        return body;
    }
}
