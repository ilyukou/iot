package by.grsu.iot.api.model.dto.validaation;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Information about validation requirements for field.
 *
 * @author Ilyukou Ilya
 * @see StringValidation
 * @see RequiredField
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldStringValidation {

    private Integer min;
    private Integer max;
    private Boolean required = false;
    private Boolean spaceAllowed;

    public FieldStringValidation(StringValidation stringValidation, RequiredField requiredField) {
        if (stringValidation != null) {
            this.min = stringValidation.min();
            this.max = stringValidation.max();
            this.spaceAllowed = stringValidation.spaceAllowed();
        }
        if (requiredField != null) {
            this.required = requiredField.required();
        }
    }
}
