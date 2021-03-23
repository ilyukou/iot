package by.grsu.iot.service.domain.state;

public class SetDeviceRequest extends StateRequest {
    public SetDeviceRequest(String token, String state) {
        super(token, state);
    }

    public SetDeviceRequest() {
    }
}
