package by.grsu.iot.service.domain.request.device;

import by.grsu.iot.service.annotation.CollectionValidation;
import by.grsu.iot.service.annotation.StringValidation;
import by.grsu.iot.service.domain.DataTransferObject;

import java.util.List;

public class DeviceFormUpdate implements DataTransferObject {

    @StringValidation(min = 2, max = 16, spaceAllowed = true, required = false)
    private String name;

    @CollectionValidation(minSize = 2, maxSize = 10)
    @StringValidation(min = 2, max = 16, required = false)
    private List<String> states;

    public DeviceFormUpdate(String name, List<String> states) {
        this.name = name;
        this.states = states;
    }

    public DeviceFormUpdate() {
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
