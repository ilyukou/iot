package by.grsu.iot.api.util;

import by.grsu.iot.api.model.annotation.CollectionValidation;
import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.exception.BadRequestApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Util for validation field by {@link StringValidation} annotation.
 *
 * @author Ilyukou Ilya
 */
public class EntityFieldValidationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityFieldValidationUtil.class);

    public static void validateString(Object object) {
        ObjectUtil.getAnnotationFields(object, StringValidation.class)
                .forEach(field -> EntityFieldValidationUtil.validateObjectField(field, object));
    }

    private static void validateObjectField(Field field, Object object) {
        StringValidation annotation = field.getAnnotation(StringValidation.class);

        Object fieldValue = getValue(field, object);

        if (fieldValue == null) {
            LOGGER.trace("Field value is null: " + fieldValue);
            return;
        }

        if (Collection.class.isAssignableFrom(field.getType())) {
            Collection collection;

            try {
                collection = (Collection) field.get(object);
                collection.forEach(elm -> validateStringFieldValue(field.getName(), elm, annotation));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new BadRequestApplicationException(field.getName(), "Error while validate a array");
            }
        } else {
            validateStringFieldValue(field.getName(), fieldValue, annotation);
        }
    }

    private static void validateStringFieldValue(String fieldName, Object fieldValue, StringValidation annotation) {
        if (String.class.isAssignableFrom(fieldValue.getClass())) {
            validateString(fieldName, (String) fieldValue, annotation);
            return;
        }

        throw new BadRequestApplicationException(fieldName,
                fieldName + " not supported to validate");
    }

    private static Object getValue(Field field, Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void validateString(String fieldName, String string, StringValidation annotation) {
        if (string.length() < annotation.min()) {
            throw new BadRequestApplicationException(fieldName,
                    fieldName + " length is less than " + annotation.min());
        }

        if (string.length() > annotation.max()) {
            throw new BadRequestApplicationException(fieldName,
                    fieldName + " length is more than " + annotation.max());
        }

        if (!annotation.spaceAllowed() && string.contains(" ")) {
            throw new BadRequestApplicationException(fieldName, fieldName + " has spaces");
        }
    }

    public static void validateCollection(Object body) {
        ObjectUtil.getAnnotationFields(body, CollectionValidation.class).forEach(e -> validateCollectionField(body, e));
    }

    public static void validateCollectionField(Object object, Field field) {
        CollectionValidation annotation = field.getAnnotation(CollectionValidation.class);

        Object fieldValue = getValue(field, object);

        if (fieldValue == null){
            return;
        }

        Collection<?> collection;

        try {
            collection = (Collection<?>) field.get(object);

            validateCollectionFieldByParam(annotation, collection, fieldValue, field);

        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new BadRequestApplicationException(field.getName(), "Error while validate a array");
        }
    }

    public static void validateCollectionFieldByParam(
            CollectionValidation annotation, Collection<?> collection, Object fieldValue, Field field
    ) {

        if (collection.size() < annotation.minSize()) {
            throw new BadRequestApplicationException(field.getName(),
                    "Collection size is " + collection.size() + " and less than " + annotation.minSize());

        }

        if (collection.size() > annotation.maxSize()) {
            throw new BadRequestApplicationException(field.getName(),
                    "Collection size is " + collection.size() + " and more than " + annotation.minSize());

        }
    }

    public static void checkRequiredField(Object object) {
        ObjectUtil.getAnnotationFields(object, RequiredField.class)
                .forEach(field -> EntityFieldValidationUtil.checkRequiredField(field, object));
    }

    private static void checkRequiredField(Field field, Object object){
        RequiredField annotation = field.getAnnotation(RequiredField.class);

        Object fieldValue = getValue(field, object);

        if (annotation.required() && fieldValue == null) {
            throw new BadRequestApplicationException(field.getName(), field.getName() + " required");
        }
    }
}
