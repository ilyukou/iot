package by.grsu.iot.telegram;

import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.UpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBot.class);

    @Value("${by.grsu.iot.telegram.bot.token}")
    private String token;

    @Value("${by.grsu.iot.telegram.bot.username}")
    private String username;

    private final UpdateService updateService;

    public TelegramBot(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostConstruct
    public void start() {
        LOGGER.info("username: {}", username);
    }

    public void executeTelegramResponse(TelegramResponse telegramResponse, Update update) throws TelegramApiException {
        LOGGER.info("Received telegramResponse {}", telegramResponse);

        if(update.hasCallbackQuery()){
            AnswerCallbackQuery callbackQuery = new AnswerCallbackQuery();
            callbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
            execute(callbackQuery);
        }

        if (telegramResponse.hasSendMessage()) {
            execute(telegramResponse.getSendMessage());

        } else if (telegramResponse.hasAnswerCallbackQuery()) {
            execute(telegramResponse.getAnswerCallbackQuery());

        } else if (telegramResponse.hasEditMessageText()) {
            execute(telegramResponse.getEditMessageText());

        } else if (telegramResponse.hasDeleteMessage()) {
            execute(telegramResponse.getDeleteMessage());

        } else if (telegramResponse.hasEditMessageReplyMarkup()) {
            execute(telegramResponse.getEditMessageReplyMarkup());

        } else if (telegramResponse.hasEditMessageText()) {
            execute(telegramResponse.getEditMessageText());

        } else if (telegramResponse.hasSendMediaGroup()) {
            execute(telegramResponse.getSendMediaGroup());

        } else {
            LOGGER.error("Not execute method for such Telegram Response {0z}", telegramResponse);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LOGGER.info("Received update {}", update);
        try {
            executeTelegramResponse(updateService.handleReceivedUpdate(update), update);
        } catch (TelegramApiException e) {
            LOGGER.warn("Error while send response {0}", e);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::onUpdateReceived);
    }
}
