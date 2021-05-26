package by.grsu.iot.api.model.dto.user;

import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

/**
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateForm implements DataTransferObject {

    @StringValidation(min = 5, max = 32)
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    @StringValidation(min = 8, max = 128)
    private String password;
}
