package by.grsu.iot.access.interf.pagination;

import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;

/**
 * Service to check access for read {@link PaginationInfo} and pages
 *
 * @author Ilyukou Ilya
 */
public interface ThingPaginationAccessService {

    /**
     * Check access for read {@link PaginationInfo}
     * @param projectId {@link Project#getId()}
     * @param whoRequestedUsername {@link User#getUsername()}
     * @throws NotAccessForOperationApplicationException if user not access for read
     */
    void checkPaginationInfoReadAccess(Long projectId, String whoRequestedUsername) throws NotAccessForOperationApplicationException;

    /**
     * Check access for read {@link Project} {@link IotThing} pages
     * @param projectId {@link Project#getId()}
     * @param whoRequestedUsername {@link User#getUsername()}
     * @throws NotAccessForOperationApplicationException if user not access for read
     */
    void checkPageReadAccess(Long projectId, String whoRequestedUsername) throws NotAccessForOperationApplicationException;
}
