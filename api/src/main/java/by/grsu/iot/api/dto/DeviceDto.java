package by.grsu.iot.api.dto;

import by.grsu.iot.model.sql.Device;

import java.util.LinkedList;
import java.util.List;

/**
 * DTO for {@link Device}
 */
public class DeviceDto {

    private Long id;

    private Long project;

    private String name;

    private String state;

    private List<String> states = new LinkedList<>();

    private String token;

    public DeviceDto() {
    }

    public DeviceDto(Device device) {
        this.id = device.getId();
        this.name = device.getName();
        this.token = device.getToken();
        this.states = device.getStates();
        this.state = device.getState();

        if (device.getProject() != null) {
            this.project = device.getProject().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
