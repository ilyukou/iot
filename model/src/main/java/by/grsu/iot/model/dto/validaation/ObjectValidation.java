package by.grsu.iot.model.dto.validaation;

import java.util.Map;

/**
 * Information about validation requirements for objects
 *
 * @author Ilyukou Ilya
 */
public class ObjectValidation {

    private Map<String, FieldStringValidation> fields;

    public ObjectValidation(Map<String, FieldStringValidation> fields) {
        this.fields = fields;
    }

    public ObjectValidation() {
    }

    public Map<String, FieldStringValidation> getFields() {
        return fields;
    }

    public void setFields(Map<String, FieldStringValidation> fields) {
        this.fields = fields;
    }
}
