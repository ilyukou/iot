package by.grsu.iot.service.validation.access.interf;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.Sensor;

/**
 * Service to validate access for operation with {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
public interface SensorAccessValidationService {

    void checkReadAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException;

    void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    void checkUpdateAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException;

    void checkDeleteAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException;
}
