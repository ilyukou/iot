package by.grsu.iot.service.interf;

import by.grsu.iot.service.domain.DeviceState;

public interface DeviceStateService {
    DeviceState getState(String remoteState, String token);
    DeviceState setState(String newState, String token);
}
