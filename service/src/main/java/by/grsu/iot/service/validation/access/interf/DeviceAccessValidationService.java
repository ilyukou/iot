package by.grsu.iot.service.validation.access.interf;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.service.exception.NotAccessForOperationApplicationException;

/**
 * Service to validate access for operation with {@link Device}
 *
 * @author Ilyukou Ilya
 */
public interface DeviceAccessValidationService {

    void checkPaginationInfoReadAccess(Long project, String whoRequestedUsername) throws NotAccessForOperationApplicationException;

    void checkPageReadAccess(Long projectId, String whoRequestedUsername) throws NotAccessForOperationApplicationException;

    void checkReadAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException;

    void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    void checkUpdateAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException;

    void checkDeleteAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException;
}
