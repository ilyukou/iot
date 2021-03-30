package by.grsu.iot.access.interf.crud;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.model.sql.User;

/**
 * Service to validate access for operation with {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
public interface SensorAccessService {

    /**
     * Check the possibility of reading {@link Sensor}
     * @param username {@link User#getUsername()}
     * @param sensorId {@link Sensor#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkReadAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of creating {@link Sensor} in {@link Project}
     * @param username {@link User#getUsername()}
     * @param projectId {@link Project#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of updating {@link Sensor}
     * @param username {@link User#getUsername()}
     * @param sensorId {@link Sensor#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkUpdateAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of deleting {@link Sensor}
     * @param username {@link User#getUsername()}
     * @param sensorId {@link Sensor#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkDeleteAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException;
}
