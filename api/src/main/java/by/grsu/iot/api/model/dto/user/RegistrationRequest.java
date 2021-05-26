package by.grsu.iot.api.model.dto.user;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

/**
 * The model that the application expects to register a user
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest implements DataTransferObject {

    @RequiredField
    @StringValidation(min = 5, max = 32)
    private String username;

    @RequiredField
    @StringValidation(min = 8, max = 128)
    private String password;

    @RequiredField
    @Email(message = "Email should be valid")
    private String email;
}

