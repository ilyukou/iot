package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.LogoutTelegramService;
import by.grsu.iot.telegram.telegramService.interf.MenuTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectListTelegramService;
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
    private final ProjectListTelegramService projectListTelegramService;
    private final LogoutTelegramService logoutTelegramService;
    private final TelegramUserService telegramUserService;

    public MenuTelegramServiceImpl(
            MenuTelegramMessageService menuTelegramMessageService,
            ProjectListTelegramService projectListTelegramService,
            LogoutTelegramService logoutTelegramService,
            TelegramUserService telegramUserService
    ) {
        this.menuTelegramMessageService = menuTelegramMessageService;
        this.projectListTelegramService = projectListTelegramService;
        this.logoutTelegramService = logoutTelegramService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public String getMessageText(Update update) {
        return menuTelegramMessageService.getWelcomeText();
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
        return Arrays.asList(projectListTelegramService, logoutTelegramService);
    }
}
