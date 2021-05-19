package by.grsu.iot.service.util;

import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.dto.sort.RequestSortType;
import by.grsu.iot.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;

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

        Match(true).of(
                Case($(projectFormUpdate.getName() != null), run(() -> p.setName(projectFormUpdate.getName()))),
                Case($(projectFormUpdate.getTitle() != null), run(() -> p.setTitle(projectFormUpdate.getTitle())))
        );

        return p;
    }

    public static Device updateField(Device device, DeviceFormUpdate deviceFormUpdate) {
        Device d = SerializationUtils.clone(device);

        Match(true).of(
                Case($(deviceFormUpdate.getName() != null), run(() -> d.setName(deviceFormUpdate.getName()))),
                Case($(deviceFormUpdate.getStates() != null), run(() -> d.setStates(deviceFormUpdate.getStates())))
        );

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

    public static Sensor updateField(Sensor sensor, SensorFormUpdate sensorFormUpdate) {
        Sensor s = SerializationUtils.clone(sensor);

        if (sensorFormUpdate.getName() != null) {
            s.setName(sensorFormUpdate.getName());
        }

        return s;
    }

    public static <T extends Annotation> T getMethodAnnotation(Class<?> clazz, String methodName, Class<T> annotation) {
        try {
            return clazz.getMethod(methodName).getAnnotation(annotation);
        } catch (NoSuchMethodException e) {
            // FIXME Throw exception without a declare exception
            throw new IllegalArgumentException("NoSuchMethodException");
        }
    }

    public static Pageable convertToPageable(RequestSortType type, String field, Integer size, Integer page) {
        return PageRequest.of(page, size, setSort(null, type, field));
    }

    private static Sort setSort(Sort sort, RequestSortType type, String field) {
        return setOrder(setField(sort, field), type);
    }

    private static Sort setOrder(Sort sort, RequestSortType type) {
        return type == RequestSortType.descending ? sort.descending() : sort.ascending();
    }

    private static Sort setField(Sort sort, String field) {
        return sort == null ? Sort.by(field) : sort.and(Sort.by(field));
    }

    public static List<SensorValue> convertToSensorValue(List<SensorValueElasticsearch> values) {
        return values.stream().map(SensorValue::new).collect(Collectors.toList());
    }
}
