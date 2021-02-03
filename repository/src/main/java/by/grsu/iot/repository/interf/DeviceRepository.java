package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;

public interface DeviceRepository {
    Device create(Project project, String name);

    Device getById(Long id);

    Device update(Device device);

    void delete(Long id);

    Device getByToken(String token);

    boolean isExist(Long id);

    boolean isExist(String token);
}
