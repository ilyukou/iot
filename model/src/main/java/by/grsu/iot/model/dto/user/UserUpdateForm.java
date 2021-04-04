package by.grsu.iot.model.dto.user;

import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

import javax.validation.constraints.Email;

public class UserUpdateForm implements DataTransferObject {

    @StringValidation(min = 5, max = 32)
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    @StringValidation(min = 8, max = 128)
    private String password;

    public UserUpdateForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserUpdateForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
