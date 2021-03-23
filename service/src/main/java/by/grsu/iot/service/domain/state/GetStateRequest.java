package by.grsu.iot.service.domain.state;

public class GetStateRequest extends StateRequest {

    public GetStateRequest(String token) {
        super(token, null);
    }

    public GetStateRequest() {
    }
}
