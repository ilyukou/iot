package by.grsu.iot.api.model.dto.email;

import by.grsu.iot.api.model.annotation.RequiredField;
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
public class SendEmailCodeRequest implements DataTransferObject {

    @Email(message = "Email should be valid")
    @RequiredField
    private String address;

    @RequiredField
    private EmailCodeType type;
}