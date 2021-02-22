package by.grsu.iot.model.api;

import java.util.List;

public class DeviceForm {
    private Long project;
    private String name;
    private String state;
    private List<String> states;

    public DeviceForm(Long project, String name, String state, List<String> states) {
        this.project = project;
        this.name = name;
        this.state = state;
        this.states = states;
    }

    public DeviceForm() {
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
