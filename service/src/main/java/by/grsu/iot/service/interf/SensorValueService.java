package by.grsu.iot.service.interf;

import by.grsu.iot.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.model.sql.Sensor;

import java.util.List;

/**
 * Service layer for CRUD operation with {@link SensorValue}
 *
 * @author Ilyukou Ilya
 */
public interface SensorValueService {

    /**
     * Add {@link SensorValue}
     *
     * @param token {@link Sensor#getToken()}
     * @param form with data
     */
    void add(String token, SensorValue form);

    /**
     * Get {@link List} of {@link SensorValue}
     *
     * @param token {@link Sensor#getToken()}
     * @param from since when to look
     * @param to what time to look for
     * @return {@link List} of {@link SensorValue}
     */
    List<SensorValue> get(String token, Long from, Long to);

    /**
     * Get one {@link SensorValue}
     *
     * @param token {@link Sensor#getToken()}
     * @return {@link SensorValue}
     */
    SensorValue getOneValue(String token);
}
