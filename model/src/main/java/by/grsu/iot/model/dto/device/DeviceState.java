package by.grsu.iot.model.dto.device;

public class DeviceState {

    private String token;

    private String state;

    public DeviceState(String token, String state) {
        this.token = token;
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
