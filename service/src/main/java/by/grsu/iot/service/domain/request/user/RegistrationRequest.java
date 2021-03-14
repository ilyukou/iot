package by.grsu.iot.service.domain.request.user;

import by.grsu.iot.service.annotation.Validation;
import by.grsu.iot.service.domain.DataTransferObject;

import javax.validation.constraints.Email;

/**
 * The model that the application expects to register a user
 */
public class RegistrationRequest implements DataTransferObject {

    @Validation(min = 5, max = 32)
    private String username;

    @Validation(min = 8, max = 128)
    private String password;

    @Email(message = "Email should be valid")
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

