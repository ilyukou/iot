package by.grsu.iot.model.dto.validaation;

import by.grsu.iot.model.annotation.RequiredField;
import by.grsu.iot.model.annotation.StringValidation;

/**
 * Information about validation requirements for field.
 *
 * @author Ilyukou Ilya
 * @see StringValidation
 * @see RequiredField
 */
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

    public FieldStringValidation() {
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getSpaceAllowed() {
        return spaceAllowed;
    }

    public void setSpaceAllowed(Boolean spaceAllowed) {
        this.spaceAllowed = spaceAllowed;
    }
}
