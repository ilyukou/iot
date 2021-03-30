package by.grsu.iot.access.interf.crud;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;

/**
 * Service to check access for operation with {@link Project}
 *
 * @author Ilyukou Ilya
 */
public interface ProjectAccessService {

    /**
     * Check the possibility of reading {@link Project}
     * @param username {@link User#getUsername()}
     * @param projectId {@link Project#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkReadAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of creating {@link Project}
     * @param username {@link User#getUsername()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkCreateAccess(String username) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of updating {@link Project}
     * @param username {@link User#getUsername()}
     * @param projectId {@link Project#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkUpdateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    /**
     * Check the possibility of deleting {@link Project}
     * @param username {@link User#getUsername()}
     * @param projectId {@link Project#getId()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkDeleteAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;
}
