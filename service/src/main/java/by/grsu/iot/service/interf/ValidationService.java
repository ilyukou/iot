package by.grsu.iot.service.interf;

import by.grsu.iot.service.domain.validaation.ObjectValidation;

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
    Map<String, ObjectValidation> getValidationRuleForAllRequestEntity();
}
