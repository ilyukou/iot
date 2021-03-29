package by.grsu.iot.model.sql;

import by.grsu.iot.model.util.CollectionUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "device")
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

    public Device() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void addState(String state) {
        this.states.add(state);
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
