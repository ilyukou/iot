package by.grsu.iot.model.dto.thing.device;

import by.grsu.iot.model.dto.DataTransferObject;

public class DeviceState implements DataTransferObject {

    private String token;

    private String state;

    public DeviceState(String token, String state) {
        this.token = token;
        this.state = state;
    }

    public DeviceState(String state) {
        this.state = state;
    }

    public DeviceState() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
