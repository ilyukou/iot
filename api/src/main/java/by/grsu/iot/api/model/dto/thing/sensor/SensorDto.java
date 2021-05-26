package by.grsu.iot.api.model.dto.thing.sensor;

import by.grsu.iot.api.model.dto.thing.IotThingDto;
import by.grsu.iot.api.model.sql.IotThing;
import by.grsu.iot.api.model.sql.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for {@link Sensor}
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorDto extends IotThingDto {

    private List<SensorValue> values;

    public SensorDto(IotThing iotThing) {
        super(iotThing);
    }

    public SensorDto(IotThing iotThing, List<SensorValue> values) {
        super(iotThing);
        this.values = values;
    }
}
