package by.grsu.iot.api.model.dto.thing.device;

import by.grsu.iot.api.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceState implements DataTransferObject {

    private String token;

    private String state;

    public DeviceState(String state) {
        this.state = state;
    }
}
