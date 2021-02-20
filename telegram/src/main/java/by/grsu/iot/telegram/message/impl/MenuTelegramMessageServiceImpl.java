package by.grsu.iot.telegram.message.impl;

import by.grsu.iot.telegram.message.interf.MenuTelegramMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application-telegram-message.properties")
public class MenuTelegramMessageServiceImpl implements MenuTelegramMessageService {


    @Value("${menu.welcome}")
    private String welcome;

    @Value("${menu.logout}")
    private String logout;

    @Value("${menu.projects}")
    private String projects;

    @Value("${menu.projects-not-found}")
    private String projectsNotFound;

    @Override
    public String getWelcomeText() {
        return welcome;
    }

    @Override
    public String getLogoutText() {
        return logout;
    }

    @Override
    public String getProjectsText() {
        return projects;
    }

    @Override
    public String getProjectsNotFoundText() {
        return projectsNotFound;
    }
}
