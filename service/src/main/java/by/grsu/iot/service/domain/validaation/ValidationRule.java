package by.grsu.iot.service.domain.validaation;

import by.grsu.iot.service.annotation.StringValidation;

public class ValidationRule {
    private int min;
    private int max;
    private boolean required;
    private boolean spaceAllowed;

    public ValidationRule(StringValidation annotation) {
        this.min = annotation.min();
        this.max = annotation.max();
        this.required = annotation.required();
        this.spaceAllowed = annotation.spaceAllowed();
    }

    public ValidationRule() {
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isSpaceAllowed() {
        return spaceAllowed;
    }

    public void setSpaceAllowed(boolean spaceAllowed) {
        this.spaceAllowed = spaceAllowed;
    }
}
