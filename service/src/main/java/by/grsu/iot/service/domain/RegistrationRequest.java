package by.grsu.iot.service.domain;

import javax.validation.constraints.NotNull;

/**
 * The model that the application expects to register a user
 */
public class RegistrationRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    public RegistrationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public RegistrationRequest() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
