package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.model.sql.User;

public interface TelegramUserRepository {

    /**
     *  Create {@link TelegramUser}.
     * @param telegramRawUser raw telegram user
     * @return created {@link TelegramUser}
     */
    TelegramUser create(final org.telegram.telegrambots.meta.api.objects.User telegramRawUser);

    /**
     * Get {@link TelegramUser} by {@link TelegramUser#getUserId()}
     * @param userId {@link TelegramUser#getUserId()}
     * @return {@link TelegramUser} or {@code null}
     */
    TelegramUser get(final Integer userId);

    /**
     * Update exist {@link TelegramUser}
     * @param telegramUser to update
     * @return updated {@link TelegramUser}
     */
    TelegramUser update(final TelegramUser telegramUser);

    /**
     * Delete {@link TelegramUser} from repository and from {@link User}
     * @param telegramUser to delete
     * @return {@code true} if success delete or {@code false}
     */
    boolean delete(final TelegramUser telegramUser);

    /**
     * Is exist {@link TelegramUser} by {@link TelegramUser#getUserId()}
     * @param userId {@link TelegramUser#getUserId()}
     * @return {@code true} if exist or {@code false}
     */
    boolean isExist(final Integer userId);
}
