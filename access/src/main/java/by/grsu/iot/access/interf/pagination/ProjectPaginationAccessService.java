package by.grsu.iot.access.interf.pagination;

import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;

/**
 * Service to check access for read {@link PaginationInfo} and pages
 *
 * @author Ilyukou Ilya
 */
public interface ProjectPaginationAccessService {

    /**
     * Check access for read {@link PaginationInfo}
     * @param whoBeingAskedUsername  who being asked {@link User#getUsername()}
     * @param whoRequestedUsername who requested {@link User#getUsername()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkPaginationInfoReadAccess(String whoBeingAskedUsername, String whoRequestedUsername) throws NotAccessForOperationApplicationException;

    /**
     * Check access for read {@link User} {@link Project} page
     * @param whoBeingAskedUsername  who being asked {@link User#getUsername()}
     * @param whoRequestedUsername who requested {@link User#getUsername()}
     * @throws NotAccessForOperationApplicationException if {@link User} not access for read
     */
    void checkPageReadAccess(String whoBeingAskedUsername, String whoRequestedUsername) throws NotAccessForOperationApplicationException;
}
