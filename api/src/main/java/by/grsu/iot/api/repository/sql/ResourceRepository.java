package by.grsu.iot.api.repository.sql;

import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.model.sql.Resource;
import by.grsu.iot.api.model.sql.User;

/**
 * Repository for CRUD operation with {@link Resource}
 *
 * @author Ilyukou Ilya
 */
public interface ResourceRepository {

    /**
     * Create {@link Resource}
     * @param fileName {@link Resource#getFileName()}
     * @param projectId {@link Project#getId()}
     * @return created {@link Resource}
     */
    Resource create(String fileName, Long projectId);

    /**
     * Update {@link Resource}
     * @param resource {@link Resource} to update
     * @return updated {@link Resource}
     */
    Resource update(Resource resource);

    /**
     * Get {@link Resource} by {@link Resource#getId()}
     * @param id {@link Resource#getId()}
     * @return {@link Resource}
     */
    Resource getById(Long id);

    /**
     * Is exist {@link Resource} with such {@link Resource#getId()}
     * @param id {@link Resource#getId()}
     * @return true if exist or false
     */
    Boolean isExist(Long id);

    /**
     * Is exist {@link Resource} with such {@link Resource#getFileName()}
     * @param filename {@link Resource#getFileName()}
     * @return true if exist or false
     */
    Boolean isExist(String filename);

    /**
     * Delete {@link Resource} with such {@link Resource#getId()}
     * @param id {@link Resource#getId()}
     * @return true if deleted or false
     */
    Boolean delete(Long id);

    /**
     * Get file owner {@link User#getUsername()}
     * @param filename {@link Resource#getFileName()}
     * @return {@link User#getUsername()}
     */
    String getOwnerUsername(String filename);
}
