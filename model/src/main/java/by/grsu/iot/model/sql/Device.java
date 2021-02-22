package by.grsu.iot.model.sql;

import by.grsu.iot.model.api.DeviceForm;
import by.grsu.iot.model.util.ModelUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "device")
public class Device extends IotThing {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

    private String state;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "name")
    @Column(name = "states")
    @CollectionTable(name = "device_states", joinColumns = @JoinColumn(name = "device_id"))
    private List<String> states = new ArrayList<>();

    public Device(BaseEntity baseEntity) {
        super(baseEntity);
    }

    public Device(Long id, Date created, Date updated, Status status,
                  String name, String token,
                  Project project, String state, List<String> states) {
        super(id, created, updated, status, name, token);
        this.project = project;
        this.state = state;
        this.states = states;
    }

    public Device(Device device) {
        this(device.getId(), device.getCreated(), device.getUpdated(), device.getStatus(),
                device.getName(), device.getToken(),
//                FIXME java.lang.StackOverflowError
//                device.getProject() != null ? new Project(device.getProject()) : null,
                null,
                device.getState(), device.getStates());
    }

    public Device() {
    }

    public Device(DeviceForm deviceForm) {
        this.state = deviceForm.getState();
        this.states = deviceForm.getStates();
        setName(deviceForm.getName());
    }

    public Device updateField(DeviceForm deviceForm) {
        this.state = deviceForm.getState();
        this.states = deviceForm.getStates();
        setName(deviceForm.getName());

        return this;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

        if(!Objects.equals(state, device.state)) return false;

        if(states.size() == 0  && device.states.size() == 0) return true;

        return ModelUtil.listEqualsIgnoreOrder(states, device.states);
    }
}
