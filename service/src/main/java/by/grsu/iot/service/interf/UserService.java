package by.grsu.iot.service.interf;

import by.grsu.iot.model.api.RegistrationRequest;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.validation.validator.RegistrationRequestValidator;

/**
 * Service layer for CRUD operation with {@link User}
 */
public interface UserService {

    /**
     * Create {@link User}.
     * Method throw {@link BadRequestException} if {@code registrationRequest} not valid see {@link RegistrationRequestValidator}
     * or {@link User} exist with such {@link User#getUsername()} or {@link User#getEmail()}
     * @param registrationRequest request
     * @return created {@link User}
     */
    User create(final RegistrationRequest registrationRequest);

    /**
     * Get {@link User} by {@link User#getUsername()}
     * Method throw {@link EntityNotFoundException} if {@link User} does not exist
     * @param username {@link User#getUsername()}
     * @return {@link User} with such {@link User#getUsername()}
     * or throw {@link EntityNotFoundException}
      */
    User getByUsername(final String username);

    /**
     * Return {@link User} if exist with such {@link User#getUsername()} and {@link User#getPassword()}
     * @param username {@link User#getUsername()}
     * @param password {@link User#getPassword()}
     * @return {@link User} or null
     */
    User getUser(String username, String password);

    /**
     * Update {@link User}
     * @param user {@link User} to update
     * @return updated {@link User}
     */
    User update(User user);
}