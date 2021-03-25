package by.grsu.iot.service.interf.pagination;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.model.dto.pagination.PaginationInfo;

import java.util.List;

/**
 * Service for the return of an array of {@link Project} in parts
 *
 * @author Ilyukou Ilya
 */
public interface ProjectPaginationService {

    /**
     * Get pagination info about {@link User} projects
     *
     * @param whoBeingAskedUsername who being asked {@link User#getUsername()}
     * @param whoRequestedUsername  who request {@link User#getUsername()}
     * @return {@link PaginationInfo}
     */
    PaginationInfo getPaginationInfo(String whoBeingAskedUsername, String whoRequestedUsername);

    /**
     * Get a {@link User} page with {@link List} of {@link Project}
     *
     * @param page                  required page
     * @param whoBeingAskedUsername who being asked {@link User#getUsername()}
     * @param whoRequestedUsername  who request {@link User#getUsername()}
     * @return {@link List} of {@link Project}
     */
    List<Project> getProjectsFromPage(Integer page, String whoBeingAskedUsername, String whoRequestedUsername);
}
