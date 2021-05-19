package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JPA Repository for {@link Device}
 *
 * @author Ilyukou Ilya
 */
@Repository
public interface DeviceJpaRepository extends JpaRepository<Device, Long> {

    Optional<Device> findDeviceByToken(String token);

    Optional<Set<Device>> findDevicesByProject(Project project);

    @Query(value = "select d.project_id from device d where d.id = ?1", nativeQuery = true)
    Long findProjectId(Long deviceId);


    @Query(value = "select d.id from device d where d.project_id = ?1", nativeQuery = true)
    List<Long> findDeviceIdsByProjectId(Long projectId);

    @Modifying
    @Query(value = "update device d set d.state = ?1, d.active = ?2 where d.token = ?3", nativeQuery = true)
    void changeState(String state, Date date, String token);

    @Query(value = "select d.state from device d where d.token = ?1", nativeQuery = true)
    String getDeviceStateByToken(String token);

    @Query(value = "select count(*) from device d where d.project_id = ?1", nativeQuery = true)
    Integer getDevicesSize(Long projectId);

    Page<Device> findAllByProject(Project project, Pageable pageable);
}
