package by.grsu.iot.service.interf;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.service.domain.response.DeviceStateDto;

/**
 * Service for change a {@link Device} state
 */
public interface DeviceStateService {

    /**
     * Get state
     *
     * If a remoteState equals a current {@link Device#getState()}, than return {@code null}
     *
     * @param remoteState device known state
     * @param token {@link Device#getToken()}
     * @return {@link DeviceState}
     */
    DeviceState getState(String remoteState, String token);

    /**
     * Set state
     *
     * if the device does not pick up the new state, it will return {@code null}
     *
     * @param newState new state
     * @param token {@link Device#getToken()}
     * @return {@link DeviceStateDto}
     */
    DeviceState setState(String newState, String token);

    void removeDevice(String token);

    void removeRequest(String token);
}
