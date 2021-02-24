package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.KeyboardButton;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.MapTelegramService;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class BackTelegramServiceImpl implements BackTelegramService {

    @Value("${by.grsu.iot.telegram.bot.back.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.back.label}")
    private String LABEL;

    private final MapTelegramService mapTelegramService;
    private final TelegramUserService telegramUserService;

    public BackTelegramServiceImpl(MapTelegramService mapTelegramService, TelegramUserService telegramUserService) {
        this.mapTelegramService = mapTelegramService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("back")){
            return getWelcomeTelegramResponse(user, update);
        }

        throw new BadRequestException();
    }

    @Override
    public TelegramResponse getWelcomeTelegramResponse(TelegramUser user, Update update) {
        if(user.peekState().equals(getServiceState())){
            user.popState();
        }
        user.popState();
        user = telegramUserService.update(user);
        return mapTelegramService.getWelcomeTelegramResponseByTelegramService(user, update);
    }

    @Override
    public String getMessageText(Update update) {
        throw new IllegalArgumentException();
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
        throw new IllegalArgumentException();
    }
}
