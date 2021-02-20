package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.ProjectService;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.MapTelegramService;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.message.interf.ProjectTelegramMessageService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectManageTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectTelegramService;
import by.grsu.iot.telegram.template.InlineKeyboardMarkupTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@PropertySource("classpath:application-telegram.properties")
public class ProjectTelegramServiceImpl implements ProjectTelegramService {

    @Value("${by.grsu.iot.telegram.bot.project.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.project.label}")
    private String LABEL;

    private final TelegramUserService telegramUserService;
    private final ProjectTelegramMessageService projectTelegramMessageService;
    private final BackTelegramService backTelegramService;
    private final ProjectService projectService;
    private final ProjectManageTelegramService projectManageTelegramService;

    public ProjectTelegramServiceImpl(
            TelegramUserService telegramUserService,
            ProjectTelegramMessageService projectTelegramMessageService,
            BackTelegramService backTelegramService,
            ProjectService projectService, ProjectManageTelegramService projectManageTelegramService) {
        this.telegramUserService = telegramUserService;
        this.projectTelegramMessageService = projectTelegramMessageService;
        this.backTelegramService = backTelegramService;
        this.projectService = projectService;
        this.projectManageTelegramService = projectManageTelegramService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if(!update.hasCallbackQuery()){
            throw new BadRequestException();
        }

        if (update.getCallbackQuery().getData().equals("back")){
            return backTelegramService.getWelcomeTelegramResponse(user, update);
        }

        if(update.getCallbackQuery().getData().contains("page")){
            String[] page = update.getCallbackQuery().getData().split(" ");

            if(page.length != 2){
                throw new BadRequestException();
            }

            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(String.valueOf(user.getUserId()));
            editMessageText.setText(getMessageText(update));
            editMessageText.setReplyMarkup(InlineKeyboardMarkupTemplate
                    .getMarkup(getKeyboard(user, Integer.valueOf(page[1])), getMaxColumnInRow()));

            editMessageText.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());
            editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
            editMessageText.setParseMode(getParseMode());

            return new TelegramResponse(editMessageText, update);
        }

        if (update.getCallbackQuery().getData().contains("project")){
            String[] page = update.getCallbackQuery().getData().split(" ");

            if(page.length != 2){
                throw new BadRequestException();
            }

            user.addState(projectManageTelegramService.getServiceState());
            user = telegramUserService.update(user);
            return projectManageTelegramService.getWelcomeEditMessageTextTelegramResponse(user, update);
        }

        throw new BadRequestException();
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
    public List<InlineKeyboardButton> getKeyboardButtons(TelegramUser user, final Update update) {
        return getKeyboard(user, 1);
    }

    private List<InlineKeyboardButton> getKeyboard(TelegramUser user, Integer pageCount){

        Integer pageSize = projectService.getCountOfProjectPage(user.getUsername(), user.getUsername());

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        if (pageCount <= pageSize && pageCount > 0){
            List<Project> projects = projectService.getProjectsFromPage(pageCount, user.getUser().getUsername());

            projects.forEach(project -> {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(project.getName());
                button.setCallbackData("project " + project.getId());

                buttons.add(button);
            });
        }

        if (pageCount > 1){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("<<");
            button.setCallbackData("page " + (pageCount - 1));

            buttons.add(button);
        }

        if (pageSize > pageCount){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(">>");
            button.setCallbackData("page " + (pageCount + 1));

            buttons.add(button);
        }


        getSubServices().forEach(telegramService -> {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setCallbackData(telegramService.getServiceState());
            button.setText(telegramService.getServiceLabel());

            buttons.add(button);
        });

        return buttons;
    }

    @Override
    public List<? extends TelegramService> getSubServices() {
        return Collections.singletonList(backTelegramService);
    }

    @Override
    public TelegramResponse refresh(TelegramUser user, Update update) {
        return getWelcomeTelegramResponse(user, update);
    }
}
