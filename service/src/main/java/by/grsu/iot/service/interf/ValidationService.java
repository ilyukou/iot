package by.grsu.iot.service.interf;

import by.grsu.iot.service.domain.ValidationRule;

import java.util.Map;

/**
 * Service for getting validation rules for entity
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
