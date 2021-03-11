package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.domain.AuthenticationRequest;
import by.grsu.iot.service.domain.AuthenticationUser;
import by.grsu.iot.service.domain.RegistrationRequest;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.validation.validator.RegistrationRequestValidator;

/**
 * Service layer for CRUD operation with {@link User}
 */
public interface UserCrudService {

    /**
     * Create {@link User}.
     * Method throw {@link BadRequestException} if {@code registrationRequest} not valid see {@link RegistrationRequestValidator}
     * or {@link User} exist with such {@link User#getUsername()} or {@link User#getEmail()}
     * @param registrationRequest request
     * @return created {@link User}
     */
    User create(final RegistrationRequest registrationRequest);

    /**
     * Authenticate user
     *
     * @param authenticationRequest authentication data with {@link User#getUsername()} and {@link User#getPassword()}
     * @return {@link AuthenticationRequest}
     */
    AuthenticationUser authenticate(AuthenticationRequest authenticationRequest);

    /**
     * Get {@link User} by {@link User#getUsername()}
     * Method throw {@link EntityNotFoundException} if {@link User} does not exist
     * @param username {@link User#getUsername()}
     * @return {@link User} with such {@link User#getUsername()}
     * or throw {@link EntityNotFoundException}
     */
    User getByUsername(final String username);



    /**
     * Update {@link User}
     *
     * @param user {@link User} to update
     * @return updated {@link User}
     */
    User update(final User user);
}
