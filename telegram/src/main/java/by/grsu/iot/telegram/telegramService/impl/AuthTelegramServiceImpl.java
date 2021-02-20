package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.service.interf.UserService;
import by.grsu.iot.telegram.domain.KeyboardButton;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.MapTelegramService;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.MenuTelegramService;
import by.grsu.iot.telegram.message.interf.AuthTelegramMessageService;
import by.grsu.iot.telegram.telegramService.interf.AuthTelegramService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application-telegram.properties")
public class AuthTelegramServiceImpl implements AuthTelegramService {

    @Value("${by.grsu.iot.telegram.bot.auth.state}")
    private String SERVICE_STATE;

    @Value("${by.grsu.iot.telegram.bot.auth.label}")
    private String SERVICE_LABEL;

    private final AuthTelegramMessageService authTelegramMessageService;
    private final MenuTelegramService menuTelegramService;
    private final TelegramUserService telegramUserService;
    private final UserService userService;

    public AuthTelegramServiceImpl(AuthTelegramMessageService authTelegramMessageService,
                                   MenuTelegramService menuTelegramService,
                                   TelegramUserService telegramUserService,
                                   UserService userService) {
        this.authTelegramMessageService = authTelegramMessageService;
        this.menuTelegramService = menuTelegramService;
        this.telegramUserService = telegramUserService;
        this.userService = userService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if(user == null){
            return handlerNotExistUser(update);
        } else {
            return handlerExistUser(user, update);
        }
    }

    @Override
    public TelegramResponse getWelcomeTelegramResponse(TelegramUser user, Update update) {
        return handleReceivedUpdate(user, update);
    }

    @Override
    public String getMessageText(Update update) {
        throw new IllegalArgumentException();
    }

    @Override
    public String getServiceLabel() {
        return SERVICE_LABEL;
    }

    @Override
    public String getServiceState() {
        return SERVICE_STATE;
    }

    @Override
    public List<? extends TelegramService> getSubServices() {
        throw new IllegalArgumentException();
    }

    @Override
    public TelegramResponse refresh(TelegramUser user, Update update) {
        return null;
    }

    private TelegramResponse handlerNotExistUser(final Update update){

        TelegramUser user = telegramUserService.create(update.getMessage().getFrom());

        user.addState(getServiceState());

        user = telegramUserService.update(user);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(user.getUserId()));
        message.setText(
                authTelegramMessageService.getWelcomeText() + "\n"
                        + "\n" + authTelegramMessageService.getCredentialText()
        );
        message.setParseMode(ParseMode.HTML);

        return new TelegramResponse(message);
    }
    
    private TelegramResponse handlerExistUser(final TelegramUser user, final Update update){
        if(!update.hasMessage() || !update.getMessage().hasText()){
            throw new BadRequestException("Bad request");
        }

        String text = update.getMessage().getText();

        String[] credential = text.split(" ");

        if(credential.length != 2){
            throw new BadRequestException("Bad request");
        }

        User usr = userService.getUser(credential[0], credential[1]);

        if(usr == null){
            throw new BadRequestException("Not found user with such credential");
        }

        usr.setTelegramUser(user);
        usr = userService.update(usr);

        TelegramUser telegramUser = SerializationUtils.clone(user);
        telegramUser.setUser(usr);
        telegramUser.addState(menuTelegramService.getServiceState());

        telegramUser = telegramUserService.update(telegramUser);

        return menuTelegramService.getWelcomeTelegramResponse(telegramUser, update);
    }
}
