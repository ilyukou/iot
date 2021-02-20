package by.grsu.iot.telegram.interf;

import by.grsu.iot.telegram.domain.TelegramResponse;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    TelegramResponse handleReceivedUpdate(Update update);
}
