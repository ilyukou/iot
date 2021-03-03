package by.grsu.iot.service.validation.interf;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.service.exception.BadRequestException;

import java.util.List;

/**
 * Service that validate a {@link Device} fields.
 *
 * @author Ilya Ilyukou
 */
public interface DeviceValidationService {

    /**
     * Method to validate {@link Device#getName()}
     *
     * @param name {@link Device#getName()}
     * @throws BadRequestException if field incorrect
     */
    void validateName(String name) throws BadRequestException;

    /**
     * Method to validate {@link Device#getStates()}
     *
     * @param states {@link Device#getStates()}
     * @throws BadRequestException if field incorrect
     */
    void validateStates(List<String> states) throws BadRequestException;

    /**
     * Method to validate {@link Device#getState()}
     *
     * @param state {@link Device#getState()}
     * @throws BadRequestException if field incorrect
     */
    void validateState(String state) throws BadRequestException;
}
