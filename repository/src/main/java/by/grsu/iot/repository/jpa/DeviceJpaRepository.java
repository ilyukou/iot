package by.grsu.iot.repository.jpa;

import by.grsu.iot.model.sql.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceJpaRepository extends JpaRepository<Device, Long> {
    Optional<Device> findDeviceByToken(String token);
}
