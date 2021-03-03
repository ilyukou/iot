package by.grsu.iot.service.validation.interf;

import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.exception.BadRequestException;

/**
 * Service that validate a {@link User} fields.
 *
 * @author Ilya Ilyukou
 */
public interface UserValidationService {

    /**
     * Method to validate {@link User#getPassword()}
     *
     * @param password {@link User#getPassword()}
     * @throws BadRequestException if field incorrect
     */
    void validatePassword(String password) throws BadRequestException;

    /**
     * Method to validate {@link User#getUsername()}
     *
     * @param username {@link User#getUsername()}
     * @throws BadRequestException if field incorrect
     */
    void validateUsername(String username) throws BadRequestException;

    /**
     * Method to validate {@link User#getEmail()}
     *
     * @param email {@link User#getEmail()}
     * @throws BadRequestException if field incorrect
     */
    void validateEmail(String email) throws BadRequestException;
}
