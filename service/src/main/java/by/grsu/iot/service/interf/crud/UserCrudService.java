package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.domain.request.user.AuthenticationRequest;
import by.grsu.iot.service.domain.request.user.RegistrationRequest;
import by.grsu.iot.service.domain.response.AuthenticationUser;

/**
 * Service layer for CRUD operation with {@link User}
 */
public interface UserCrudService {

    /**
     * Create {@link User}.
     *
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
     *
     * @param username {@link User#getUsername()}
     * @return {@link User} with such {@link User#getUsername()}
     */
    User getByUsername(final String username);
}
