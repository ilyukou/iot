package by.grsu.iot.api.model.dto.thing.device;

import by.grsu.iot.api.model.annotation.CollectionValidation;
import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.dto.DataTransferObject;
import by.grsu.iot.api.model.sql.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Used to send data to create a device
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceForm implements DataTransferObject {

    private Long project;

    @RequiredField
    @StringValidation(min = 2, max = 16)
    private String state;

    @RequiredField
    @StringValidation(min = 2, max = 16, spaceAllowed = true)
    private String name;

    @RequiredField
    @CollectionValidation(minSize = 2, maxSize = 10)
    @StringValidation(min = 2, max = 16)
    private List<String> states;

    public Device convert() {
        Device device = new Device();

        device.setName(getName());
        device.setStates(getStates());
        device.setState(getState());

        return device;
    }
}
