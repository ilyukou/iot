package by.grsu.iot.service.interf.pagination;

import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.domain.pagination.PaginationInfo;

import java.util.List;

/**
 * Service for the return of an array of {@link IotThing} in parts
 *
 * @author Ilyukou Ilya
 */
public interface ThingPaginationService {

    /**
     * Get pagination info about {@link IotThing} in {@link  Project}
     *
     * @param project  {@link Project#getId()}
     * @param username who requested {@link User#getUsername()}
     * @return {@link PaginationInfo}
     */
    PaginationInfo getPaginationInfo(Long project, String username);

    /**
     * Get a {@link Project} page with {@link List} of {@link IotThing}
     *
     * @param project  @link Project#getId()}
     * @param page     a required page with {@link IotThing}
     * @param username who requested {@link User#getUsername()}
     * @return {@link List} of {@link IotThing}
     */
    List<? extends IotThing> getThingsFromProjectPage(Long project, Integer page, String username);
}
