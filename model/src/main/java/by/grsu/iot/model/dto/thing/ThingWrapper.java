package by.grsu.iot.model.dto.thing;

import by.grsu.iot.model.dto.thing.device.DeviceDto;
import by.grsu.iot.model.dto.thing.sensor.SensorDto;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Sensor;

public class ThingWrapper {
    private ThingEnum type;
    private Object entity;

    public ThingWrapper(IotThing iotThing) {
        if (iotThing.getClass().equals(Device.class)) {
            this.type = ThingEnum.device;
            this.entity = new DeviceDto((Device) iotThing);

        } else if (iotThing.getClass().equals(Sensor.class)){
            this.type = ThingEnum.sensor;
            this.entity = new SensorDto((Sensor) iotThing);
        }
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
