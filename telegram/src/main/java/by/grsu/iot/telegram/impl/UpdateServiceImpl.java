package by.grsu.iot.telegram.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.*;
import by.grsu.iot.telegram.telegramService.interf.AuthTelegramService;
import by.grsu.iot.telegram.util.UpdateUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateServiceImpl implements UpdateService {

    private static final String MENU_STATE_NAME = "menu";
    private static final String PROJECT_STATE_NAME = "project";
    private static final String AUTH_STATE_NAME = "auth";

    private final ErrorService errorService;
    private final TelegramUserService telegramUserService;
    private final MapTelegramService mapTelegramService;
    private final AuthTelegramService authTelegramService;

    public UpdateServiceImpl(
            ErrorService errorService,
            TelegramUserService telegramUserService,
            MapTelegramService mapTelegramService,
            AuthTelegramService authTelegramService) {
        this.errorService = errorService;
        this.telegramUserService = telegramUserService;
        this.mapTelegramService = mapTelegramService;
        this.authTelegramService = authTelegramService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(Update update) {
        try {
            return handle(update);
        } catch (BadRequestException e){
            TelegramUser user = telegramUserService.get(UpdateUtil.getChatId(update));

            if(user == null){
                return errorService.getBadRequestMessage("Bad request", update);
            } else {
                return mapTelegramService.getWelcomeTelegramResponseByTelegramService(user, update);
            }
        }
    }

    private TelegramResponse handle(Update update){
        Integer userId = UpdateUtil.getChatId(update);

        if(telegramUserService.isExist(userId)){
            TelegramUser user = telegramUserService.get(userId);

            return mapTelegramService.handleReceivedUpdateByTelegramService(user, update);

        } else {
            return authTelegramService.handleReceivedUpdate(null, update);
        }
    }
}
