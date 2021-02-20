package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.LogoutTelegramService;
import by.grsu.iot.telegram.telegramService.interf.MenuTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectTelegramService;
import by.grsu.iot.telegram.message.interf.MenuTelegramMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:application-telegram.properties")
public class MenuTelegramServiceImpl implements MenuTelegramService {

    @Value("${by.grsu.iot.telegram.bot.menu.label}")
    private String LABEL;

    @Value("${by.grsu.iot.telegram.bot.menu.state}")
    private String STATE;

    private final MenuTelegramMessageService menuTelegramMessageService;
    private final ProjectTelegramService projectTelegramService;
    private final LogoutTelegramService logoutTelegramService;
    private final BackTelegramService backTelegramService;
    private final TelegramUserService telegramUserService;

    public MenuTelegramServiceImpl(MenuTelegramMessageService menuTelegramMessageService,
                                   ProjectTelegramService projectTelegramService,
                                   LogoutTelegramService logoutTelegramService,
                                   BackTelegramService backTelegramService,
                                   TelegramUserService telegramUserService) {
        this.menuTelegramMessageService = menuTelegramMessageService;
        this.projectTelegramService = projectTelegramService;
        this.logoutTelegramService = logoutTelegramService;
        this.backTelegramService = backTelegramService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if(!update.hasCallbackQuery()){
            throw new BadRequestException();
        }

        if(update.getCallbackQuery().getData().equals(projectTelegramService.getServiceState())){
            user.addState(projectTelegramService.getServiceState());
            user = telegramUserService.update(user);
            return projectTelegramService.getWelcomeTelegramResponse(user, update);
        }

        if(update.getCallbackQuery().getData().equals(logoutTelegramService.getServiceState())){
            return logoutTelegramService.getWelcomeTelegramResponse(user, update);
        }

        if(update.getCallbackQuery().getData().equals(backTelegramService.getServiceState())){
            return getWelcomeTelegramResponse(user, update);
        }

        throw new BadRequestException();
    }

    @Override
    public String getMessageText(Update update) {
        return menuTelegramMessageService.getWelcomeText();
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
        return Arrays.asList(projectTelegramService, logoutTelegramService);
    }

    @Override
    public TelegramResponse refresh(TelegramUser user, Update update) {
        throw new BadRequestException();
    }
}
