package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.domain.ProjectThing;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.ProjectService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectManageTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectManageTelegramServiceImpl implements ProjectManageTelegramService {

    @Value("${by.grsu.iot.telegram.bot.projectManage.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.projectManage.label}")
    private String LABEL;

    private final BackTelegramService backTelegramService;
    private final ProjectService projectService;

    public ProjectManageTelegramServiceImpl(
            BackTelegramService backTelegramService,
            ProjectService projectService) {
        this.backTelegramService = backTelegramService;
        this.projectService = projectService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if(!update.hasCallbackQuery()){
            throw new BadRequestException();
        }

        if (update.getCallbackQuery().getData().equals("back")){
            return backTelegramService.getWelcomeTelegramResponse(user, update);
        }

        throw new BadRequestException();
    }

    @Override
    public String getMessageText(Update update) {
        if (!update.hasCallbackQuery() || !update.getCallbackQuery().getData().contains("project")){
            return "Not exist callback query.";
        }

        String[] data = update.getCallbackQuery().getData().split(" ");

        if(data.length != 2){
            return "Not valid callback query.";
        }
        Project project;

        try {
            project = projectService.getById(Long.valueOf(data[1]));
        } catch (Exception e){
            return "Not found project.";
        }

        StringBuilder builder = new StringBuilder();
        builder
                .append("<p><strong>")
                .append(project.getName())
                .append("</strong></p>");

        builder.append("\n");

        builder
                .append("<p><i>")
                .append(project.getTitle())
                .append("</i></p>");

        return builder.toString();
    }

    @Override
    public List<InlineKeyboardButton> getKeyboardButtons(TelegramUser user, final Update update) {
        if(!update.hasCallbackQuery() || !update.getCallbackQuery().getData().contains("project")){
            throw new BadRequestException();
        }

        String[] data = update.getCallbackQuery().getData().split(" ");

        if(data.length != 2){
            throw new BadRequestException();
        }

        List<ProjectThing> things = projectService.getThings(Long.valueOf(data[1]), user.getUser().getUsername());

        return things.stream().map(thing ->
                InlineKeyboardButton.builder()
                        .callbackData("thing " + thing.getEntity() + " " + thing.getId())
                        .text(thing.getEntity()).build()).collect(Collectors.toList());
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

    @Override
    public TelegramResponse refresh(TelegramUser user, Update update) {
        return null;
    }
}
