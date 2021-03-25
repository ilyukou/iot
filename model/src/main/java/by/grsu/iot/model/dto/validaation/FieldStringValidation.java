package by.grsu.iot.model.dto.validaation;

import by.grsu.iot.service.annotation.StringValidation;

/**
 * Information about validation requirements for field.
 *
 * @author Ilyukou Ilya
 * @see StringValidation
 */
public class FieldStringValidation {

    private Integer min;
    private Integer max;
    private Boolean required;
    private Boolean spaceAllowed;

    public FieldStringValidation(StringValidation stringValidation) {
        this.min = stringValidation.min();
        this.max = stringValidation.max();
        this.required = stringValidation.required();
        this.spaceAllowed = stringValidation.spaceAllowed();
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
