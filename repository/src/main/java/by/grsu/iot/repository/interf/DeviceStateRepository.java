package by.grsu.iot.repository.interf;

import by.grsu.iot.model.domain.DeviceState;

public interface DeviceStateRepository {
    void put(DeviceState deviceState);

    DeviceState get(String token);

    boolean isExist(String token);

    boolean isWait(String token);

    boolean isCompleted(String token);

    void delete(String token);

    DeviceState getAndDelete(String token);
}
