package by.grsu.iot.model.dto.user;

import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

/**
 * Used to send authorization data to the controller
 *
 * @author Ilyukou Ilya
 */
public class AuthenticationRequest implements DataTransferObject {

    @StringValidation(min = 5, max = 32)
    private String username;

    @StringValidation(min = 8, max = 128)
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
