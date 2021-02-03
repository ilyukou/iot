package by.grsu.iot.api.service.interf;

import by.grsu.iot.api.dto.DeviceState;
import by.grsu.iot.model.sql.Device;

public interface DeviceService {

    Device create(Long projectId, String name, String username);

    Device getById(Long id, String username);

    boolean deleteById(Long id, String username);

    void update(Long id, String name, String state, String username);

    Device update(Device device);

    void setState(String token, String state);

    Device getByToken(String token);

    DeviceState getStateWhenDeviceStateNotEqualState(String token, String deviceState);
}
