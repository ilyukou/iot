package by.grsu.iot.service.domain.form;

import by.grsu.iot.model.sql.Device;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DeviceForm implements Form {

    @NotNull
    private Long project;

    @NotNull
    private String name;

    @NotNull
    private String state;

    @NotNull
    private List<String> states;

    public DeviceForm(Long project, String name, String state, List<String> states) {
        this.project = project;
        this.name = name;
        this.state = state;
        this.states = states;
    }

    public DeviceForm() {
    }

    public Device convert() {
        Device device = new Device();

        device.setName(name);
        device.setStates(states);
        device.setState(state);

        return device;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
