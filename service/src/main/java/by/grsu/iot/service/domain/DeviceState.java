package by.grsu.iot.service.domain;

import by.grsu.iot.model.sql.Device;

/**
 * {@link Device}
 */
public class DeviceState {

    private String state;

    public DeviceState(String state) {
        this.state = state;
    }

    public DeviceState(Device device) {
        this.state = device.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
