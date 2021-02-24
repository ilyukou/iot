package by.grsu.iot.service.interf;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.model.sql.User;

public interface TelegramUserService {

    /**
     *  Add {@link TelegramUser} to {@link User}.
     * @param telegramRawUser raw telegram user
     * @return created {@link TelegramUser}
     */
    TelegramUser create(final org.telegram.telegrambots.meta.api.objects.User telegramRawUser);

    /**
     * Update {@link TelegramUser}
     * @param telegramUser {@link TelegramUser} to update
     * @return update {@link TelegramUser}
     */
    TelegramUser update(TelegramUser telegramUser);

    /**
     * Is exist {@link TelegramUser} by {@link TelegramUser#getUserId()}
     * @param userId {@link TelegramUser#getUserId()}
     * @return {@code true} if exist or {@code false}
     */
    boolean isExist(final Integer userId);

    /**
     * Get {@link TelegramUser} by {@link TelegramUser#getUserId()}
     * @param userId {@link TelegramUser#getUserId()}
     * @return {@link TelegramUser} or {@code null}
     */
    TelegramUser get(final Integer userId);

    /**
     * Delete {@link TelegramUser} from {@link User}
     * @param user {@link TelegramUser}
     */
    void delete(TelegramUser user);
}
