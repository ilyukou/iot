package by.grsu.iot.service.validation.interf;

import by.grsu.iot.service.exception.BadRequestException;

public interface UserValidationService {
    public void validatePassword(String password) throws BadRequestException;
    public void validateUsername(String username) throws BadRequestException;
    public void validateEmail(String email) throws BadRequestException;
}
