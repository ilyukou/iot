package by.grsu.iot.service.util;

import by.grsu.iot.service.annotation.Validation;
import by.grsu.iot.service.exception.BadRequestException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EntityFieldValidationUtil {

    public static void validateObject(Object object) {
        getAnnotationFields(object)
                .forEach(field -> EntityFieldValidationUtil.validateObjectField(field, object));
    }

    private static List<Field> getAnnotationFields(Object object) {
        return FieldUtils.getAllFieldsList(object.getClass())
                .stream()
                .peek(field -> field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(Validation.class))
                .collect(Collectors.toList());
    }

    private static void validateObjectField(Field field, Object object) {
        Validation annotation = field.getAnnotation(Validation.class);

        Object fieldValue = getValue(field, object);

        if (annotation.required() && fieldValue == null) {
            throw new BadRequestException(field.getName(), field.getName() + " required");
        }

        if (!annotation.required() && fieldValue == null) {
            return;
        }

        if (Collection.class.isAssignableFrom(field.getType())) {
            Collection collection;

            try {
                collection = (Collection) field.get(object);
                collection.forEach(elm -> validateFieldValue(field.getName(), elm, annotation));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new BadRequestException(field.getName(), "Error while validate a array");
            }
        } else {
            validateFieldValue(field.getName(), fieldValue, annotation);
        }
    }

    private static void validateFieldValue(String fieldName, Object fieldValue, Validation annotation) {
        if (String.class.isAssignableFrom(fieldValue.getClass())) {
            validateString(fieldName, (String) fieldValue, annotation);
            return;
        }

        throw new BadRequestException(fieldName,
                fieldName + " not supported to validate");
    }

    private static Object getValue(Field field, Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void validateString(String fieldName, String string, Validation annotation) {
        if (string.length() < annotation.min()) {
            throw new BadRequestException(fieldName,
                    fieldName + " length is less than " + annotation.min());
        }

        if (string.length() > annotation.max()) {
            throw new BadRequestException(fieldName,
                    fieldName + " length is more than " + annotation.max());
        }

        if (!annotation.spaceAllowed() && string.contains(" ")) {
            throw new BadRequestException(fieldName, fieldName + " has spaces");
        }
    }
}
