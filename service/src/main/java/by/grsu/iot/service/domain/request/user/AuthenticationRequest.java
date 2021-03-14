package by.grsu.iot.service.domain.request.user;

import by.grsu.iot.service.annotation.Validation;
import by.grsu.iot.service.domain.DataTransferObject;

/**
 * Used to send authorization data to the controller
 */
public class AuthenticationRequest implements DataTransferObject {

    @Validation(min = 5, max = 32)
    private String username;

    @Validation(min = 8, max = 128)
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
