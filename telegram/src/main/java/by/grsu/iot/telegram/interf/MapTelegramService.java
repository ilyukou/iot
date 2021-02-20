package by.grsu.iot.telegram.interf;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.telegram.domain.TelegramResponse;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MapTelegramService {
    TelegramResponse handleReceivedUpdateByTelegramService(TelegramUser user, Update update);

    TelegramResponse getBack(final TelegramUser user, final Update update);

    TelegramResponse getWelcomeTelegramResponseByTelegramService(TelegramUser user, Update update);
}
