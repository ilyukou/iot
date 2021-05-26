package by.grsu.iot.api.model.sql;

import by.grsu.iot.api.model.util.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ilyukou Ilya
 */
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device extends IotThing {

    private String state;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "name")
    @Column(name = "states")
    @CollectionTable(name = "device_states", joinColumns = @JoinColumn(name = "device_id"))
    private List<String> states = new ArrayList<>();

    public Device(BaseEntity baseEntity) {
        super(baseEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        if (!super.equals(o)) return false;
        Device device = (Device) o;

        if (!Objects.equals(state, device.state)) return false;

        if (states.size() == 0 && device.states.size() == 0) return true;

        return CollectionUtil.listEqualsIgnoreOrder(states, device.states);
    }
}
