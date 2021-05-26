package by.grsu.iot.api.model.dto.user;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.dto.DataTransferObject;
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
public class ActivateUser implements DataTransferObject {

    @RequiredField
    private String username;

    @RequiredField
    private Integer code;
}
