package by.grsu.iot.model.dto.thing.device.state;

/**
 * @author Ilyukou Ilya
 */
public class GetStateRequest extends StateRequest {

    public GetStateRequest(String token) {
        super(token, null);
    }

    public GetStateRequest() {
    }
}
