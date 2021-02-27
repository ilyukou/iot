package by.grsu.iot.service.interf;

import by.grsu.iot.model.api.DeviceForm;
import by.grsu.iot.model.api.DeviceFormUpdate;
import by.grsu.iot.model.elastic.DeviceStateElasticsearch;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.service.domain.DeviceState;

public interface DeviceService {

    Device create(DeviceForm deviceForm, String username);

    Device getById(Long id, String username);

    boolean deleteById(Long id, String username);

    Device update(Long id, DeviceFormUpdate deviceFormUpdate, String username);

    Device update(Device device);

    DeviceState setState(String token, String state);

    Device getByToken(String token);

    DeviceState getStateWhenDeviceStateNotEqualState(String token, String deviceState);

    void deleteDeviceStateChangeRequests(String token);
}
