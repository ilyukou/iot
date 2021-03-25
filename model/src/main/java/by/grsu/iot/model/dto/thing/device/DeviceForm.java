package by.grsu.iot.model.dto.thing.device;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.annotation.CollectionValidation;
import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

import java.util.List;

/**
 * Used to send data to create a device
 *
 * @author Ilyukou Ilya
 */
public class DeviceForm implements DataTransferObject {

    private Long project;

    @StringValidation(min = 2, max = 16)
    private String state;

    @StringValidation(min = 2, max = 16, spaceAllowed = true)
    private String name;

    @CollectionValidation(minSize = 2, maxSize = 10)
    @StringValidation(min = 2, max = 16)
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
