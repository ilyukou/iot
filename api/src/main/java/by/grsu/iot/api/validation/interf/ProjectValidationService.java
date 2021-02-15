package by.grsu.iot.api.validation.interf;

import by.grsu.iot.api.exception.BadRequestException;

public interface ProjectValidationService {
    public void validateName(String name) throws BadRequestException;
    public void validateTitle(String title) throws BadRequestException;
}
