package by.grsu.iot.model.sql;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Ilyukou Ilya
 */
@Entity
@Table(name = "sensor")
@NoArgsConstructor
public class Sensor extends IotThing {

    public Sensor(BaseEntity baseEntity) {
        super(baseEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IotThing)) return false;
        if (!super.equals(o)) return false;
        IotThing iotThing = (IotThing) o;
        return Objects.equals(getName(), iotThing.getName())
                && Objects.equals(getToken(), iotThing.getToken())
                && Objects.equals(getActive(), iotThing.getActive());
    }
}
