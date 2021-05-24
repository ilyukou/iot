package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.dto.PageWrapper;
import by.grsu.iot.model.dto.sort.RequestSortType;
import by.grsu.iot.model.dto.thing.sensor.SensorDto;
import by.grsu.iot.model.dto.thing.sensor.SensorForm;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;

/**
 * Service layer for CRUD operation with {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
public interface SensorCrudService {

    /**
     * Create {@link Sensor} for {@link Project} with such {@link Project#getId()}.
     *
     * @param sensorForm form with fields
     * @return created {@link Sensor}
     */
    Sensor create(SensorForm sensorForm);

    /**
     * Update {@link Sensor}
     *
     * @param id               {@link Project#getId()}
     * @param sensorFormUpdate form with fields
     * @return update {@link Sensor}
     */
    Sensor update(Long id, SensorFormUpdate sensorFormUpdate);

    /**
     * Get {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id {@link Sensor#getId()}
     * @return {@link Sensor}
     */
    Sensor getById(Long id);

    /**
     * Delete {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id {@link Sensor#getId()}
     */
    void delete(Long id);

    /**
     * Is exist {@link Sensor} by {@link Sensor#getToken()}
     *
     * @param token {@link Sensor#getToken()}
     * @return true if exist, or false
     */
    boolean isExist(String token);

    PageWrapper<SensorDto> getPage(Long project, Integer size, Integer page, RequestSortType type, String field);
}
