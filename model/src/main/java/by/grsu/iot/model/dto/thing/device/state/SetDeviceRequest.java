package by.grsu.iot.model.dto.thing.device.state;

/**
 * @author Ilyukou Ilya
 */
public class SetDeviceRequest extends StateRequest {
    public SetDeviceRequest(String token, String state) {
        super(token, state);
    }

    public SetDeviceRequest() {
    }
}
