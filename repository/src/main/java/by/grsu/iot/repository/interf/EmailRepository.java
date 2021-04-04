package by.grsu.iot.repository.interf;


import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.User;

/**
 * Repository for CRUD operation with {@link Email}
 *
 * @author Ilyukou Ilya
 */
public interface EmailRepository {

    /**
     * Find {@link Email} by {@link Email#getAddress()}
     *
     * @param address {@link Email#getAddress()}
     * @return {@link Email} from repository.
     */
    Email findByAddress(final String address);

    /**
     * Create {@link Email}
     *
     * @param email to create
     * @return created {@link Email}
     */
    Email create(final Email email);

    /**
     * Get {@link Email} by {@link Email#getId()}
     *
     * @param id {@link Email#getId()}
     * @return {@link Email} from repository.
     */
    Email getById(final Long id);

    /**
     * Update {@link Email}
     *
     * @param email to update
     * @return updated {@link Email}
     */
    Email update(final Email email);

    /**
     * Is exist {@link Email} by {@link Email#getAddress()}
     *
     * @param address {@link Email#getAddress()}
     * @return {@code true} if exist or {@code false} if not exist
     */
    boolean isExist(String address);

    /**
     * Change address for {@link User#getUsername()}
     * @param username {@link User#getUsername()}
     * @param address new address for {@link User}
     * @return updated {@link Email}
     */
    Email changeAddress(String username, String address);

    /**
     * Get code {@link Email#getCode()}
     * @param username {@link User}
     * @return {@link Email#getCode()}
     */
    Integer getCode(String username);

    /**
     * Create code for {@link Email}
     * @param address {@link Email#getAddress()}
     * @return {@link Email#getCode()}
     */
    Integer createCode(String address);
}
