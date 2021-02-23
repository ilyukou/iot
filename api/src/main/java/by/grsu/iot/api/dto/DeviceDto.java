package by.grsu.iot.api.dto;

import by.grsu.iot.model.sql.Device;

import java.util.LinkedList;
import java.util.List;

/**
 * DTO for {@link Device}
 */
public class DeviceDto extends IotThingDto {

    private String state;

    private List<String> states = new LinkedList<>();

    public DeviceDto() {
    }

    public DeviceDto(Device device) {
        super(device.getId(), device.getName(), device.getToken(),
                device.getUpdated(), // FIXME
                device.getUpdated(), device.getCreated());
        this.states = device.getStates();
        this.state = device.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }
}
