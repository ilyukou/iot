package by.grsu.iot.model.dto.thing.device.state;

/**
 * State Request superclass for basic logic with state
 *
 * @author Ilyukou Ilya
 */
public abstract class StateRequest {

    private String token;

    private String state;

    public StateRequest(String token, String state) {
        this.token = token;
        this.state = state;
    }

    public StateRequest() {
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

    @Override
    public String toString() {
        return "StateRequest{" +
                "token='" + token + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
