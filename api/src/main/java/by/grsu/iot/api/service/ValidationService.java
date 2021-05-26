package by.grsu.iot.api.service;

import by.grsu.iot.api.model.dto.validaation.FieldStringValidation;

import java.util.Map;

/**
 * Interface return a validation rule about DTO entity
 *
 * @author Ilyukou Ilya
 */
public interface ValidationService {

    /**
     * Key is a {@link Class#getSimpleName()} value is a validation rule about that object
     *
     * @return all dto entity rules
     */
    Map<String, Map<String, FieldStringValidation>> getValidationRuleForAllRequestEntity();
}
