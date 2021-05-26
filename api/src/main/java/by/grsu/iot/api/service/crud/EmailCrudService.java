package by.grsu.iot.api.service.crud;


import by.grsu.iot.api.model.dto.email.SendEmailCodeRequest;
import by.grsu.iot.api.model.sql.Email;

/**
 * Service layer for CRUD operation with {@link Email}
 *
 * @author Ilyukou Ilya
 */
public interface EmailCrudService {

    /**
     * Get {@link Email} by {@link Email#getId()}
     *
     * @param address {@link Email#getAddress()}
     * @return {@link Email}
     */
    Email getByAddress(String address);

    /**
     * Create {@link Email}
     *
     * @param email {@link Email}
     * @return created {@link Email}
     */
    Email create(Email email);

    Integer changeEmailAddress(String username, String address);

    void sendEmailCode(SendEmailCodeRequest data);
}
