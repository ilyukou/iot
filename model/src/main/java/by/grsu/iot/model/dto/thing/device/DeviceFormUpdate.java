package by.grsu.iot.model.dto.thing.device;

import by.grsu.iot.model.annotation.CollectionValidation;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Used to send data to update a device
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceFormUpdate implements DataTransferObject {

    @StringValidation(min = 2, max = 16, spaceAllowed = true)
    private String name;

    @CollectionValidation(minSize = 2, maxSize = 10)
    @StringValidation(min = 2, max = 16)
    private List<String> states;
}
