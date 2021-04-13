package by.grsu.iot.access.interf.crud;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Resource;
import by.grsu.iot.model.sql.User;

/**
 * Service to check access for operation with {@link Resource}
 *
 * @author Ilyukou Ilya
 */
public interface ResourceAccessService {

    /**
     * Check the possibility of reading {@link Resource}
     * @param username {@link User#getUsername()}
     * @param filename {@link Resource#getFileName()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkReadAccess(String username, String filename) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of creating {@link Resource}
     * @param username {@link User#getUsername()}
     * @param projectId {@link Project#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

}
