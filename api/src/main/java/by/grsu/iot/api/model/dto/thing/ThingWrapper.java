package by.grsu.iot.api.model.dto.thing;

import by.grsu.iot.api.model.dto.thing.device.DeviceDto;
import by.grsu.iot.api.model.dto.thing.sensor.SensorDto;
import by.grsu.iot.api.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.api.model.sql.Device;
import by.grsu.iot.api.model.sql.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThingWrapper {

    private ThingEnum type;
    private Object entity;

    public ThingWrapper(Device device) {
        this.type = ThingEnum.device;
        this.entity = new DeviceDto(device);
    }

    public ThingWrapper(Sensor sensor, List<SensorValue> values) {
        this.type = ThingEnum.sensor;
        this.entity = new SensorDto(sensor, values);
    }
}
