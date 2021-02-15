package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import java.util.Set;

/**
 * Repository for CRUD operation with {@link Device}
 */
public interface DeviceRepository {

    /**
     * Create {@link Device}
     * @param project which project to add
     * @param device to create
     * @return created {@link Device}
     */
    Device create(final Project project, final Device device);

    /**
     * Get {@link Device} by {@link Device#getId()}
     * @param id {@link Device#getId()}
     * @return {@link Device} or {@code null}
     */
    Device getById(final Long id);

    /**
     * Not recommended use {@link Project#getDevices()} because {@link Project#devices}
     * has {@code FetchType.LAZY}
     * @param project {@link Project}
     * @return {@link Set<Device>} or empty {@link Set}
     */
    Set<Device> getDevicesByProject(final Project project);

    /**
     * Update {@link Device}
     * @param device to update
     * @return updated {@link Device}
     */
    Device update(final Device device);

    /**
     * Get {@link Device} by {@link Device#getToken()}
     * @param token {@link Device#getToken()}
     * @return {@link Device} or {@code null}
     */
    Device getByToken(final String token);

    /**
     * Delete {@link Device} by {@link Device#getId()}
     * @param id {@link Device#getId()}
     * @return {@code true} if deleted or {@code false}
     */
    boolean delete(final Long id);

    /**
     * Is exist {@link Device} by {@link Device#getId()}
     * @param id {@link Device#getId()}
     * @return {@code true} if exist or {@code false}
     */
    boolean isExist(final Long id);
}
