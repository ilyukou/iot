package by.grsu.iot.api.service.interf;

import by.grsu.iot.api.dto.DeviceState;
import by.grsu.iot.model.sql.Device;

public interface DeviceService {

    Device create(Long projectId, Device device, String username);

    Device getById(Long id, String username);

    boolean deleteById(Long id, String username);

    Device update(Long id, Device device, String username);

    Device update(Device device);

    DeviceState setState(String token, String state);

    Device getByToken(String token);

    DeviceState getStateWhenDeviceStateNotEqualState(String token, String deviceState);

    void deleteDeviceStateChangeRequests(String token);
}
