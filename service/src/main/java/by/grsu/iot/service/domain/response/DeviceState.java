package by.grsu.iot.service.domain.response;

import by.grsu.iot.model.sql.Device;

import java.io.Serializable;

/**
 * {@link Device}
 */
public class DeviceState implements Serializable {

    private String state;

    public DeviceState(String state) {
        this.state = state;
    }

    public DeviceState(Device device) {
        this.state = device.getState();
    }

    public DeviceState() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
