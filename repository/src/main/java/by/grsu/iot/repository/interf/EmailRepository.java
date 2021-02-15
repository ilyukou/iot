package by.grsu.iot.repository.interf;


import by.grsu.iot.model.sql.Email;

/**
 * Repository for CRUD operation with {@link Email}
 */
public interface EmailRepository {

    /**
     * Find {@link Email} by {@link Email#getAddress()}
     * @param address {@link Email#getAddress()}
     * @return {@link Email} from repository.
     */
    Email findByAddress(final String address);

    /**
     * Create {@link Email}
     * @param email to create
     * @return created {@link Email}
     */
    Email create(final Email email);

    /**
     * Get {@link Email} by {@link Email#getId()}
     * @param id {@link Email#getId()}
     * @return {@link Email} from repository.
     */
    Email getById(final Long id);

    /**
     * Update {@link Email}
     * @param email to update
     * @return updated {@link Email}
     */
    Email update(final Email email);

    /**
     *  Is exist {@link Email} by {@link Email#getAddress()}
     * @param address {@link Email#getAddress()}
     * @return {@code true} if exist or {@code false} if not exist
     */
    boolean isExist(String address);
}
