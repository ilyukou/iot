package by.grsu.iot.api.validation.interf;

import by.grsu.iot.api.exception.BadRequestException;

import java.util.List;

public interface DeviceValidationService {
    public void validateName(String name) throws BadRequestException;
    public void validateStates(List<String> states) throws BadRequestException;
    public void validateState(String state) throws BadRequestException;
}
