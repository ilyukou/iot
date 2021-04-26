package by.grsu.iot.model.dto.thing;

import by.grsu.iot.model.dto.thing.device.DeviceDto;
import by.grsu.iot.model.dto.thing.sensor.SensorDto;
import by.grsu.iot.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Sensor;

import java.util.List;

/**
 * @author Ilyukou Ilya
 */
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

    public ThingWrapper() {
    }

    public ThingEnum getType() {
        return type;
    }

    public void setType(ThingEnum type) {
        this.type = type;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
