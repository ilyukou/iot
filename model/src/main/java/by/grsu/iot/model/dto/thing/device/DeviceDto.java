package by.grsu.iot.model.dto.thing.device;

import by.grsu.iot.model.dto.thing.IotThingDto;
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
        super(device);
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
