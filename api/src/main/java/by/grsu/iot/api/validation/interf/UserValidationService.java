package by.grsu.iot.api.validation.interf;

import by.grsu.iot.api.exception.BadRequestException;

public interface UserValidationService {
    public void validatePassword(String password) throws BadRequestException;
    public void validateUsername(String username) throws BadRequestException;
    public void validateEmail(String email) throws BadRequestException;
}
