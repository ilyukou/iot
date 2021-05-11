package by.grsu.iot.model.dto.thing.sensor;

import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Used to send data to update a sensor
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorFormUpdate implements DataTransferObject {

    @RequiredField
    @StringValidation(min = 2, max = 16, spaceAllowed = true)
    private String name;
}
