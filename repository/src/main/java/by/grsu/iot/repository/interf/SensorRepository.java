package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.model.sql.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Repository for CRUD operation with {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
public interface SensorRepository {

    /**
     * Get {@link User#getUsername()} who owns this sensor
     *
     * @param sensorId {@link Sensor#getId()}
     * @return {@link User#getUsername()}
     */
    String getOwnerUsername(Long sensorId);

    /**
     * Create {@link Sensor}
     *
     * @param project {@link Project#getId()}
     * @param name    {@link Sensor#getName()}
     * @return created {@link Sensor}
     */
    Sensor create(Long project, String name);

    /**
     * Get {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id {@link Sensor#getId()}
     * @return {@link Sensor}
     */
    Sensor getById(Long id);

    /**
     * Delete {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id {@link Sensor#getId()}
     * @return true if deleted, or false
     */
    boolean delete(Long id);

    /**
     * Update {@link Sensor}
     *
     * @param sensor {@link Sensor}
     * @return updated {@link Sensor}
     */
    Sensor update(Sensor sensor);

    /**
     * Is exist {@link Sensor} by {@link Sensor#getId()}
     *
     * @param id {@link Sensor#getId()}
     * @return true if exist, or false
     */
    boolean isExist(final Long id);

    /**
     * If exist {@link Sensor} by {@link Sensor#getToken()}
     *
     * @param token {@link Sensor#getToken()}
     * @return true if exist, or false
     */
    boolean isExist(String token);

    /**
     * Get {@link Sensor} by {@link Sensor#getToken()}
     *
     * @param token {@link Sensor#getToken()}
     * @return {@link Sensor}
     */
    Sensor getByToken(String token);

    /**
     * Get {@link List} of {@link Sensor#getId()} in {@link Project}
     *
     * @param projectId {@link Project#getId()}
     * @return {@link List} of {@link Sensor#getId()}
     */
    List<Long> getProjectSensorIds(Long projectId);

    /**
     * Get count of {@link Sensor} in {@link Project}
     *
     * @param projectId {@link Project#getId()}
     * @return count of {@link Sensor}
     */
    Integer getSensorsSize(Long projectId);

    String getTokenById(Long id);

    /**
     * Get {@link Page} of {@link Sensor}
     *
     * @param project  {@link Project#getId()}
     * @param pageable {@link Pageable}
     * @return page of sensors
     */
    Page<Sensor> getPage(Project project, Pageable pageable);

    boolean hasUserOwnerSensor(String username, Long sensorId);
}
