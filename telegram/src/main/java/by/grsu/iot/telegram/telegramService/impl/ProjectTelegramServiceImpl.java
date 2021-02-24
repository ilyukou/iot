package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.interf.ProjectService;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.interf.TelegramService;
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
    private final TelegramUserService telegramUserService;
    private final ProjectService projectService;

    public ProjectTelegramServiceImpl(
            BackTelegramService backTelegramService,
            TelegramUserService telegramUserService,
            ProjectService projectService
    ) {
        this.backTelegramService = backTelegramService;
        this.telegramUserService = telegramUserService;
        this.projectService = projectService;
    }

    @Override
    public String getMessageText(Update update) {
        if(!update.hasCallbackQuery() || !update.getCallbackQuery().getData().contains("project")){
            return "Error";
        }

        String[] data = update.getCallbackQuery().getData().split("-");

        if (data.length != 2){
            return "Error id";
        }

        Project project = projectService.getById(Long.valueOf(data[1]));

        return "<strong>" + project.getName() + "</strong>"
                + "\n\n"
                + "<i>" + project.getTitle() + "</i>";
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
        return Arrays.asList(backTelegramService);
    }
}
