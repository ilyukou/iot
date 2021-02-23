package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.message.interf.ProjectTelegramMessageService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectTelegramServiceImpl implements ProjectTelegramService {

    @Value("${by.grsu.iot.telegram.bot.project.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.project.label}")
    private String LABEL;

    private final BackTelegramService backTelegramService;
    private final ProjectTelegramMessageService projectTelegramMessageService;

    public ProjectTelegramServiceImpl(BackTelegramService backTelegramService, ProjectTelegramMessageService projectTelegramMessageService) {
        this.backTelegramService = backTelegramService;
        this.projectTelegramMessageService = projectTelegramMessageService;
    }

    @Override
    public String getMessageText(Update update) {
        return projectTelegramMessageService.getWelcomeText();
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
        return Arrays.asList(backTelegramService);
    }
}
