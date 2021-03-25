package by.grsu.iot.service.util;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Object util
 *
 * @author Ilyukou Ilya
 */
public class ObjectUtil {
    public static <T> boolean areAllFieldNotNull(T t) throws IllegalAccessException {
        for (Field f : t.getClass().getDeclaredFields()) {
            if (f.get(t) != null) {
                return false;
            }
        }

        return true;
    }

    public static <T> boolean hasClassAnnotatedField(Class<T> t, Class<? extends Annotation> annotation) {
        for (Field field : t.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                return true;
            }
        }

        return false;
    }

    public static List<Field> getAnnotationFields(Object object, Class<? extends Annotation> annotation) {
        return FieldUtils.getAllFieldsList(object.getClass())
                .stream()
                .peek(field -> field.setAccessible(true))
                .filter(field -> field.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    public static Project updateField(Project project, ProjectFormUpdate projectFormUpdate) {
        Project p = SerializationUtils.clone(project);

        if (projectFormUpdate.getName() != null) {
            p.setName(projectFormUpdate.getName());
        }

        if (projectFormUpdate.getTitle() != null) {
            p.setTitle(projectFormUpdate.getTitle());
        }

        return p;
    }

    public static Device updateField(Device device, DeviceFormUpdate deviceFormUpdate) {
        Device d = SerializationUtils.clone(device);

        if (deviceFormUpdate.getName() != null) {
            d.setName(deviceFormUpdate.getName());
        }

        if (deviceFormUpdate.getStates() != null) {
            d.setStates(deviceFormUpdate.getStates());
        }

        return d;
    }

    public static Object castStringToObject(String value) {
        try {

            if (value.equals("true") || value.equals("false")) {
                return Boolean.parseBoolean(value);
            }

            return Long.parseLong(value);

        } catch (NumberFormatException e) {
            return value;
        }
    }
}
