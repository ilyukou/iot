package by.grsu.iot.model.dto.thing.sensor;

import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

/**
 * Used to send data to update a sensor
 *
 * @author Ilyukou Ilya
 */
public class SensorFormUpdate implements DataTransferObject {

    @RequiredField
    @StringValidation(min = 2, max = 16, spaceAllowed = true)
    private String name;

    public SensorFormUpdate(String name) {
        this.name = name;
    }

    public SensorFormUpdate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
