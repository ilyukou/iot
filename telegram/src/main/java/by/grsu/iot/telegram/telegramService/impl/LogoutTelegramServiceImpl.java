package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.telegram.domain.KeyboardButton;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.LogoutTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class LogoutTelegramServiceImpl implements LogoutTelegramService {

    @Value("${by.grsu.iot.telegram.bot.logout.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.logout.label}")
    private String LABEL;

    @Override
    public String getMessageText(Update update) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getServiceLabel() {
        return LABEL;
    }

    @Override
    public String getServiceState() {
        return STATE;
    }

    @Override
    public List<? extends TelegramService> getSubServices() {
        throw new IllegalArgumentException();
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        throw new IllegalArgumentException();
    }

    @Override
    public TelegramResponse refresh(TelegramUser user, Update update) {
        throw new IllegalArgumentException();
    }
}
