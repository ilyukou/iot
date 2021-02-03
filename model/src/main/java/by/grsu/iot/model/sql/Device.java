package by.grsu.iot.model.sql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device")
public class Device extends IotThing {

    @ManyToOne
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

    public Device() {
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
}
