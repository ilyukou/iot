package by.grsu.iot.service.impl.model;

public class DeviceStateRequest extends DeviceStateHttpRequest {
    public DeviceStateRequest(String token, String state) {
        super(token, state);
    }

    public DeviceStateRequest() {
    }
}
