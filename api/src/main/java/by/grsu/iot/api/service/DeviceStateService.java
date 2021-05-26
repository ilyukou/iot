package by.grsu.iot.api.service;

import by.grsu.iot.api.model.dto.thing.device.DeviceState;
import by.grsu.iot.api.model.sql.Device;

/**
 * Service for change a {@link Device} state
 *
 * @author Ilyukou Ilya
 */
public interface DeviceStateService {

    /**
     * Get state update for device
     *
     * @param token {@link Device#getToken()}
     * @return changed device state, is state changed
     */
    DeviceState getState(String token);

    /**
     * Set new state to device
     *
     * @param newState {@link Device#getState()}
     * @param token    {@link Device#getToken()}
     * @return {@link DeviceState} after success device change state
     */
    DeviceState setState(String newState, String token);

    /**
     * Remove device that wait new state value.
     * Typical used, after time out exception to clean up the repository.
     *
     * @param token {@link Device#getToken()}
     */
    void removeDevice(String token);

    /**
     * Remove request to change device state.
     * Typical used, after time out exception to clean up the repository.
     *
     * @param token {@link Device#getToken()}
     */
    void removeRequest(String token);

    /**
     * @return time that a device wait before timeout.
     */
    Long getDeviceWaitTime();

    /**
     * @return time that a request wait before timeout.
     */
    Long getRequestWaitTime();
}
