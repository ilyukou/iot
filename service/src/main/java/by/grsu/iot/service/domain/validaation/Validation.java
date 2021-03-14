package by.grsu.iot.service.domain.validaation;

import by.grsu.iot.service.config.ValidationConfig;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>In-memory storage validation rules for DTO</p>
 *
 * <p>First {@link Map} key is a class name {@code Object.class.getSimpleName()}.
 * Second key is a field name of such class.</*p>
 *
 * <p>see also {@link ValidationConfig}</p>
 */
@Component
public class Validation {

    private final Map<String, Map<String, ValidationRule>> validationRules;

    public Validation(Map<String, Map<String, ValidationRule>> validationRules) {
        this.validationRules = validationRules;
    }

    public Map<String, Map<String, ValidationRule>> getValidationRules() {

        return validationRules;
    }
}
