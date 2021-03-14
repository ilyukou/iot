package by.grsu.iot.service.domain.request.device;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.service.annotation.Validation;
import by.grsu.iot.service.domain.DataTransferObject;

import java.util.List;

public class DeviceForm implements DataTransferObject {

    private Long project;

    @Validation(min = 2, max = 16)
    private String state;

    @Validation(min = 2, max = 16, spaceAllowed = true)
    private String name;

    @Validation(min = 2, max = 16)
    private List<String> states;

    public DeviceForm(Long project, String state, String name, List<String> states) {
        this.project = project;
        this.state = state;
        this.name = name;
        this.states = states;
    }

    public DeviceForm() {
    }

    public Device convert() {
        Device device = new Device();

        device.setName(getName());
        device.setStates(getStates());
        device.setState(getState());

        return device;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }
}
