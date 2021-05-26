package by.grsu.iot.api.service.crud;

import by.grsu.iot.api.model.dto.user.*;
import by.grsu.iot.api.model.sql.Status;
import by.grsu.iot.api.model.sql.User;

/**
 * Service layer for CRUD operation with {@link User}
 *
 * @author Ilyukou Ilya
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

    void activateUser(ActivateUser data);

    void update(String username, UserUpdateForm form);

    void restorePassword(RestorePasswordForm data);

    Status getDefaultUserStatus();
}
