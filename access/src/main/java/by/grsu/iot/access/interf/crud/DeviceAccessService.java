package by.grsu.iot.access.interf.crud;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;

/**
 * Service to validate access for operation with {@link Device}
 *
 * @author Ilyukou Ilya
 */
public interface DeviceAccessService {

    /**
     * Check the possibility of reading {@link Device}
     * @param username {@link User#getUsername()}
     * @param deviceId {@link Device#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkReadAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of creating {@link Device}
     * @param username {@link User#getUsername()}
     * @param projectId {@link Project#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of updating {@link Device}
     * @param username {@link User#getUsername()}
     * @param deviceId {@link Device#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkUpdateAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of deleting {@link Device}
     * @param username {@link User#getUsername()}
     * @param deviceId {@link Device#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkDeleteAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException;
}
