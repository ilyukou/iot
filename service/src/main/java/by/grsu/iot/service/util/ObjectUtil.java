package by.grsu.iot.service.util;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.domain.request.device.DeviceFormUpdate;
import by.grsu.iot.service.domain.request.project.ProjectFormUpdate;
import org.apache.commons.lang3.SerializationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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
