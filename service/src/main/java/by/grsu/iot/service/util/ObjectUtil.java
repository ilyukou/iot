package by.grsu.iot.service.util;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.domain.form.DeviceFormUpdate;
import by.grsu.iot.service.domain.form.ProjectFormUpdate;
import org.apache.commons.lang3.SerializationUtils;

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

        return d;
    }
}
