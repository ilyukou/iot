package by.grsu.iot.api.model.dto.thing.device;

import by.grsu.iot.api.model.dto.thing.IotThingDto;
import by.grsu.iot.api.model.sql.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * DTO for {@link Device}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto extends IotThingDto {

    private String state;

    private List<String> states = new LinkedList<>();

    public DeviceDto(Device device) {
        super(device);
        this.states = device.getStates();
        this.state = device.getState();
    }
}
