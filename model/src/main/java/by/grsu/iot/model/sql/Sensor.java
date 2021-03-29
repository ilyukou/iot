package by.grsu.iot.model.sql;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sensor")
public class Sensor extends IotThing {

    public Sensor(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public Sensor() {
    }
}
