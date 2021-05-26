package by.grsu.iot.api.model.dto.user;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Used to send authorization data to the controller
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements DataTransferObject {

    @RequiredField
    @StringValidation(min = 5, max = 32)
    private String username;

    @RequiredField
    @StringValidation(min = 8, max = 128)
    private String password;
}
