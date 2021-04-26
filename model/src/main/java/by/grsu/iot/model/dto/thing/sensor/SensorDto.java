package by.grsu.iot.model.dto.thing.sensor;

import by.grsu.iot.model.dto.thing.IotThingDto;
import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Sensor;

import java.util.List;

/**
 * DTO for {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
public class SensorDto extends IotThingDto {

    private List<SensorValue> values;

    public SensorDto() {
    }

    public SensorDto(IotThing iotThing) {
        super(iotThing);
    }

    public SensorDto(IotThing iotThing, List<SensorValue> values) {
        super(iotThing);
        this.values = values;
    }

    public List<SensorValue> getValues() {
        return values;
    }

    public void setValues(List<SensorValue> values) {
        this.values = values;
    }
}
