package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.ProjectService;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.message.interf.ProjectTelegramMessageService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectListTelegramService;
import by.grsu.iot.telegram.telegramService.interf.ProjectTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectListTelegramServiceImpl implements ProjectListTelegramService {

    private static final String PROJECT_PAGINATION = "projectPagination";
    private static final String PROJECT = "project";

    @Value("${by.grsu.iot.telegram.bot.projectList.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.projectList.label}")
    private String LABEL;

    private final BackTelegramService backTelegramService;
    private final ProjectTelegramMessageService projectTelegramMessageService;
    private final TelegramUserService telegramUserService;
    private final ProjectService projectService;
    private final ProjectTelegramService projectTelegramService;

    public ProjectListTelegramServiceImpl(
            BackTelegramService backTelegramService,
            ProjectTelegramMessageService projectTelegramMessageService,
            TelegramUserService telegramUserService,
            ProjectService projectService,
            ProjectTelegramService projectTelegramService
    ) {
        this.backTelegramService = backTelegramService;
        this.projectTelegramMessageService = projectTelegramMessageService;
        this.telegramUserService = telegramUserService;
        this.projectService = projectService;
        this.projectTelegramService = projectTelegramService;
    }

    @Override
    public String getMessageText(Update update) {
        return projectTelegramMessageService.getWelcomeText();
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

    @Override
    public List<InlineKeyboardButton> getKeyboardButtons(TelegramUser user, Update update) {
        List<InlineKeyboardButton> list = new ArrayList<>();

        String data = "";

        if(update.hasCallbackQuery()){
            data = update.getCallbackQuery().getData();
        }

//        if (data.equals(getServiceState())){
//            // first page need
//            if (projectService.getCountOfProjectPage(user.getUser().getUsername(), user.getUser().getUsername()) != 0){
//                list.addAll(getProjectsPage(user, 1));
//            }
//        } else if(data.contains(PROJECT_PAGINATION)){

        if(data.contains(PROJECT_PAGINATION)){
            // required page
            String[] req = data.split("-");

            if(req.length != 2){
                throw new BadRequestException();
            }
//            FIXME Получать массив проектов запрашиваемой страницы, а также значки перехода по страницам.
            list.addAll(getProjectsPage(user, Integer.valueOf(req[1])));

            if (Integer.parseInt(req[1]) > 1){
                list.add(getPreviousPagePaginationButton(Integer.parseInt(req[1]) - 1));
            }

            if (Integer.parseInt(req[1]) < projectService.getCountOfProjectPage(user.getUser().getUsername(),
                    user.getUser().getUsername())){
                list.add(getForwardPagePaginationButton(Integer.parseInt(req[1]) + 1));
            }
        } else if (projectService
                .getCountOfProjectPage(user.getUser().getUsername(), user.getUser().getUsername()) != 0){
            list.addAll(getProjectsPage(user, 1));

            if (projectService.getCountOfProjectPage(user.getUser().getUsername(),
                    user.getUser().getUsername()) > 1){
                list.add(getForwardPagePaginationButton(2));
            }
        }

        list.addAll(ProjectListTelegramService.super.getKeyboardButtons(user, update));
        return list;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if (isCallbackQueryHasTransition(user, update)){
            return ProjectListTelegramService.super.handleReceivedUpdate(user, update);
        }

        // FIXME Обработка переходов по страницам проектов и по самим проектам
        if(!update.hasCallbackQuery()){
            throw new BadRequestException();
        }

        if(update.getCallbackQuery().getData().contains(PROJECT_PAGINATION)){
            // переход по страницам
            return getWelcomeTelegramResponse(user, update);
        }

        if (update.getCallbackQuery().getData().contains(PROJECT)){
            // переход к проекту
            user.addState(projectTelegramService.getServiceState());
            user = telegramUserService.update(user);
            return projectTelegramService.getWelcomeTelegramResponse(user, update);
        }

        throw new BadRequestException();
    }

    private List<InlineKeyboardButton> getProjectsPage(TelegramUser user, Integer count){
        return projectService.getProjectsFromPage(count, user.getUser().getUsername()).stream()
                .map(project -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();

                    button.setText(project.getName());
                    button.setCallbackData(PROJECT + "-" + project.getId());

                    return button;
                }).collect(Collectors.toList());
    }

    private InlineKeyboardButton getPreviousPagePaginationButton(Integer count){
        return getPaginationButton(count, "<< Previous");
    }

    private InlineKeyboardButton getForwardPagePaginationButton(Integer count){
        return getPaginationButton(count, "Forward >>");
    }

    private InlineKeyboardButton getPaginationButton(Integer count, String data){
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(data);
        button.setCallbackData(PROJECT_PAGINATION + "-" + count);

        return button;
    }
}
