package by.grsu.iot.model.dto.thing.device;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.dto.device.DeviceState;

import java.io.Serializable;

/**
 * {@link Device}
 */
public class DeviceStateDto implements Serializable {

    private String state;

    public DeviceStateDto(String state) {
        this.state = state;
    }

    public DeviceStateDto(Device device) {
        this.state = device.getState();
    }

    public DeviceStateDto(DeviceState deviceState) {
        this.state = deviceState.getState();
    }

    public DeviceStateDto() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
