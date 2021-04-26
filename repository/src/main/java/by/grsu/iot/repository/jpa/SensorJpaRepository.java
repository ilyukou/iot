package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface SensorJpaRepository extends JpaRepository<Sensor, Long> {

    @Query(value = "select s.project_id from sensor s where s.id = ?1", nativeQuery = true)
    Long findProjectId(Long deviceId);

    Optional<Sensor> findSensorByToken(String token);

    @Query(value = "select s.id from sensor s where s.project_id = ?1", nativeQuery = true)
    List<Long> findSensorsIdsByProjectId(Long projectId);

    @Query(value = "select count(*) from sensor s where s.project_id = ?1", nativeQuery = true)
    Integer getSensorsSize(Long projectId);

    @Query(value = "select s.token from sensor s where s.id = ?1", nativeQuery = true)
    String findTokenById(Long id);
}
