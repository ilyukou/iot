package by.grsu.iot.model.dto.user;

import by.grsu.iot.service.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

import javax.validation.constraints.Email;

/**
 * The model that the application expects to register a user
 *
 * @author Ilyukou Ilya
 */
public class RegistrationRequest implements DataTransferObject {

    @StringValidation(min = 5, max = 32)
    private String username;

    @StringValidation(min = 8, max = 128)
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

