package by.grsu.iot.api.service.interf;

import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.model.sql.User;


/**
 * Service layer for CRUD operation with {@link User}
 */
public interface UserService {

    /**
     * Create {@link User}.
     * Method throw {@link BadRequestException} if {@link User} exist by {@link User#getUsername()}
     * or {@link User#getEmail()}
     * @param user to create
     * @return created {@link User}
     */
    User create(final User user);

    /**
     * Get {@link User} by {@link User#getUsername()}
     * Method throw {@link by.grsu.iot.api.exception.EntityNotFoundException} if {@link User} does not exist
     * @param username {@link User#getUsername()}
     * @return {@link User} with such {@link User#getUsername()}
     * or throw {@link by.grsu.iot.api.exception.EntityNotFoundException}
      */
    User getByUsername(final String username);
}
