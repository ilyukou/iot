package by.grsu.iot.service.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Used to send authorization data to the controller
 */
public class AuthenticationRequest implements Serializable {

    @NotNull
    private String username;

    @NotNull
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
