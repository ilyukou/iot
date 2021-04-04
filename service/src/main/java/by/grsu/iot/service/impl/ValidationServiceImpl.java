package by.grsu.iot.service.impl;

import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;
import by.grsu.iot.model.dto.validaation.FieldStringValidation;
import by.grsu.iot.model.dto.validaation.ObjectValidation;
import by.grsu.iot.service.interf.ValidationService;
import by.grsu.iot.service.util.ObjectUtil;
import org.reflections.Reflections;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    @Cacheable("validation")
    public Map<String, ObjectValidation> getValidationRuleForAllRequestEntity() {
        Reflections reflections = new Reflections("by.grsu.iot.model.dto");

        Set<Class<?>> classes = reflections.getSubTypesOf(DataTransferObject.class).stream()
                .peek(aClass -> Arrays.stream(aClass.getFields()).forEach(field -> field.setAccessible(true)))
                .filter(aClass -> ObjectUtil.hasClassAnnotatedField(aClass, StringValidation.class))
                .collect(Collectors.toSet());

        Map<String, ObjectValidation> map = new HashMap<>();

        for (Class<?> clazz : classes) {
            Map<String, FieldStringValidation> fieldsMap = new HashMap<>();

            for (Field field : clazz.getDeclaredFields()) {
                FieldStringValidation fieldStringValidation = new FieldStringValidation();
                int i = 0;

                if (field.isAnnotationPresent(StringValidation.class)){
                    fieldStringValidation.updateField(field.getAnnotation(StringValidation.class));
                    i++;
                }

                if (field.isAnnotationPresent(RequiredField.class)){
                    fieldStringValidation.updateField(field.getAnnotation(RequiredField.class));
                    i++;
                }

                if (i > 0){
                    fieldsMap.put(field.getName(), fieldStringValidation);
                }
            }

            map.put(clazz.getSimpleName(), new ObjectValidation(fieldsMap));
        }

        return map;
    }
}
