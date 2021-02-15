package by.grsu.iot.api.service.interf;

import by.grsu.iot.api.dto.validation.ValidationRule;

import java.util.Map;

/**
 * Validation Service for validate entity in controller layer
 */
public interface ValidationService {

    /**
     * Get Validation rule for entity like {@link by.grsu.iot.model.sql.User}, {@link by.grsu.iot.model.sql.Project},
     * {@link by.grsu.iot.model.sql.Device}
     * @param entity project, device, user
     * @return Validation rules. {@link String} param is equals entity field name
     */
    Map<String, ValidationRule> getRulesForEntity(String entity);
}
