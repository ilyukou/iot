package by.grsu.iot.service.validation.interf;

import by.grsu.iot.service.exception.BadRequestException;

import java.util.List;

public interface DeviceValidationService {
    void validateName(String name) throws BadRequestException;
    void validateStates(List<String> states) throws BadRequestException;
    void validateState(String state) throws BadRequestException;
}
