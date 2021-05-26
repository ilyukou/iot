package by.grsu.iot.api.service;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.dto.DataTransferObject;
import by.grsu.iot.api.model.dto.validaation.FieldStringValidation;
import by.grsu.iot.api.util.ObjectUtil;
import org.reflections.Reflections;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    @Cacheable("validation")
    public Map<String, Map<String, FieldStringValidation>> getValidationRuleForAllRequestEntity() {
        Reflections reflections = new Reflections("by.grsu.iot.api.model.dto");

        return reflections.getSubTypesOf(DataTransferObject.class).stream()
                .peek(aClass -> Arrays.stream(aClass.getFields()).forEach(field -> field.setAccessible(true)))
                .filter(aClass -> ObjectUtil.hasClassAnnotatedField(aClass, StringValidation.class))
                .collect(Collectors.toMap(Class::getSimpleName, this::getClassValidationRule));
    }

    private Map<String, FieldStringValidation> getClassValidationRule(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(StringValidation.class) || field.isAnnotationPresent(RequiredField.class))
                .collect(Collectors.toMap(Field::getName,
                        field -> new FieldStringValidation(field.getAnnotation(StringValidation.class), field.getAnnotation(RequiredField.class))));
    }
}
