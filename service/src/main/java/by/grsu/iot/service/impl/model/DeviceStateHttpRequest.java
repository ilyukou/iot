package by.grsu.iot.service.impl.model;

public class DeviceStateHttpRequest {

    private String token;

    private String state;

    private boolean isProcessed = false;

    public DeviceStateHttpRequest(String token, String state) {
        this.token = token;
        this.state = state;
    }

    public DeviceStateHttpRequest() {
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

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    @Override
    public String toString() {
        return "DeviceStateHttpRequest{" +
                "token='" + token + '\'' +
                ", state='" + state + '\'' +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
