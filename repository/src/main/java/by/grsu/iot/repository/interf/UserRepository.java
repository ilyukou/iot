package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.User;

/**
 * Repository for CRUD operation with {@link User}
 *
 * @author Ilyukou Ilya
 */
public interface UserRepository {

    /**
     * Create {@link User}
     *
     * @param user to create.
     * @return created user with id.
     */
    User create(final User user);

    /**
     * Get {@link User} from repository. If user not exist - return {@code null}
     *
     * @param id {@link User#getId()}
     * @return {@link User} with such id.
     */
    User getById(final Long id);

    /**
     * Update {@link User}.
     *
     * @param user to update
     * @return updated {@link User}
     */
    User update(final User user);

    /**
     * Get {@link User} from repository. If user not exist - return {@code null}
     *
     * @param username {@link User#getUsername()}
     * @return {@link User} with such username.
     */
    User getByUsername(final String username);

    /**
     * Is exist {@link User} with such username
     *
     * @param username {@link User#getUsername()}
     * @return {@code true} if exist, or {@code false} if not exist
     */
    boolean isExistByUsername(final String username);

    /**
     * Find {@link User}
     *
     * @param userId {@link User#getId()}
     * @return {@link User} or {@code null}
     */
    String findUsername(Long userId);

    /**
     * Get {@link User#getId()}
     *
     * @param username {@link User#getUsername()}
     * @return {@link User#getId()} or {@code null}
     */
    Long getUserId(String username);
}

