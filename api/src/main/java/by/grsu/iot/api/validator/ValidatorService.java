package by.grsu.iot.api.validator;

import by.grsu.iot.api.exception.BadRequestException;
import org.springframework.validation.Validator;

public interface ValidatorService extends Validator {
    void validateParam(Object target) throws BadRequestException;
}
