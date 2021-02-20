package by.grsu.iot.service.validation.interf;

import by.grsu.iot.service.exception.BadRequestException;

public interface ProjectValidationService {
    public void validateName(String name) throws BadRequestException;
    public void validateTitle(String title) throws BadRequestException;
}
