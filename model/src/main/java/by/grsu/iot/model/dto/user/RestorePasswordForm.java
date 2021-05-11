package by.grsu.iot.model.dto.user;

import by.grsu.iot.model.annotation.RequiredField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordForm extends AuthenticationRequest {

    @RequiredField
    private Integer code;
}
