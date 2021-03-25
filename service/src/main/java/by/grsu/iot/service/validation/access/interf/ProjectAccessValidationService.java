package by.grsu.iot.service.validation.access.interf;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.exception.NotAccessForOperationApplicationException;

/**
 * Service to check access for operation with {@link Project}
 *
 * @author Ilyukou Ilya
 */
public interface ProjectAccessValidationService {

    void checkPaginationInfoReadAccess(String whoBeingAskedUsername, String whoRequestedUsername) throws NotAccessForOperationApplicationException;

    void checkPageReadAccess(String whoBeingAskedUsername, String whoRequestedUsername) throws NotAccessForOperationApplicationException;

    void checkReadAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    void checkCreateAccess(String username) throws NotAccessForOperationApplicationException;

    void checkUpdateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;

    void checkDeleteAccess(String username, Long projectId) throws NotAccessForOperationApplicationException;
}
