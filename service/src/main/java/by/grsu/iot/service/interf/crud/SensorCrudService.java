package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.dto.thing.sensor.SensorForm;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.model.sql.User;

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
     * @param username   who request a create device {@link User#getUsername()}
     * @return created {@link Sensor}
     */
    Sensor create(SensorForm sensorForm, String username);

    /**
     * Update {@link Sensor}
     *
     * @param id               {@link Project#getId()}
     * @param sensorFormUpdate form with fields
     * @param username         who request a update device {@link User#getUsername()}
     * @return update {@link Sensor}
     */
    Sensor update(Long id, SensorFormUpdate sensorFormUpdate, String username);

    /**
     * Get {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id       {@link Sensor#getId()}
     * @param username who request a device {@link User#getUsername()}
     * @return {@link Sensor}
     */
    Sensor getById(Long id, String username);

    /**
     * Delete {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id       {@link Sensor#getId()}
     * @param username who request a delete sensor {@link User#getUsername()}
     */
    void delete(Long id, String username);

    /**
     * Is exist {@link Sensor} by {@link Sensor#getToken()}
     *
     * @param token {@link Sensor#getToken()}
     * @return true if exist, or false
     */
    boolean isExist(String token);
}
