package by.grsu.iot.model.dto.thing.sensor;

import by.grsu.iot.model.dto.thing.IotThingDto;
import by.grsu.iot.model.sql.Sensor;

/**
 * DTO for {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
public class SensorDto extends IotThingDto {

    public SensorDto() {
    }

    public SensorDto(Sensor sensor) {
        super(sensor);
    }

}
