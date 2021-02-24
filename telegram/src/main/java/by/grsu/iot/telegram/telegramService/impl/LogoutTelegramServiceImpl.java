package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.KeyboardButton;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.AuthTelegramService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.LogoutTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class LogoutTelegramServiceImpl implements LogoutTelegramService {

    @Value("${by.grsu.iot.telegram.bot.logout.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.logout.label}")
    private String LABEL;

    private final BackTelegramService backTelegramService;
    private final TelegramUserService telegramUserService;
    private final AuthTelegramService authTelegramService;

    @Lazy
    public LogoutTelegramServiceImpl(
            BackTelegramService backTelegramService,
            TelegramUserService telegramUserService,
            AuthTelegramService authTelegramService
    ) {
        this.backTelegramService = backTelegramService;
        this.telegramUserService = telegramUserService;
        this.authTelegramService = authTelegramService;
    }

    @Override
    public TelegramResponse getWelcomeTelegramResponse(TelegramUser user, Update update) {
        telegramUserService.delete(user);

        return authTelegramService.getWelcomeTelegramResponse(null, update);
    }

    @Override
    public String getMessageText(Update update) {
        return "Text:" + getServiceLabel();
    }

    @Override
    public TelegramUser update(TelegramUser user) {
        return telegramUserService.update(user);
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
        return Collections.singletonList(backTelegramService);
    }
}
