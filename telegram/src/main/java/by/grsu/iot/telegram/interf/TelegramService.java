package by.grsu.iot.telegram.interf;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.template.InlineKeyboardMarkupTemplate;
import by.grsu.iot.telegram.util.UpdateUtil;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

public interface TelegramService {
    TelegramResponse handleReceivedUpdate(TelegramUser user, Update update);

    default TelegramResponse getWelcomeTelegramResponse(final TelegramUser user, final Update update){
        if (update.hasCallbackQuery()){
            return getWelcomeEditMessageTextTelegramResponse(user, update);
        }

        return getWelcomeSendMessageTelegramResponse(user, update);
    }

    String getMessageText(Update update);

    String getServiceLabel();

    String getServiceState();

    List<? extends TelegramService> getSubServices();

    TelegramResponse refresh(final TelegramUser user, final Update update);

    default byte getMaxColumnInRow(){
        return 1;
    }

    default TelegramResponse getWelcomeEditMessageTextTelegramResponse(final TelegramUser user, final Update update){
        EditMessageText editMessageText = new EditMessageText();

        editMessageText.setChatId(String.valueOf(user.getUserId()));
        editMessageText.setText(getMessageText(update));
        editMessageText.setReplyMarkup(InlineKeyboardMarkupTemplate.getMarkup(
                getKeyboardButtons(user, update),
                getMaxColumnInRow()
        ));

        editMessageText.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setParseMode(getParseMode());

        return new TelegramResponse(editMessageText, update);
    }

    default TelegramResponse getWelcomeSendMessageTelegramResponse(final TelegramUser user, final Update update){
        SendMessage sendMessage = new SendMessage();

        sendMessage.setReplyMarkup(InlineKeyboardMarkupTemplate.getMarkup(
                getKeyboardButtons(user, update),
                getMaxColumnInRow()
        ));
        sendMessage.setChatId(String.valueOf(UpdateUtil.getChatId(update)));
        sendMessage.setText(getMessageText(update));
        sendMessage.setParseMode(ParseMode.HTML);

        return new TelegramResponse(sendMessage);
    }

    default InlineKeyboardButton getServiceInlineKeyboardButton(){
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(getServiceLabel());
        button.setCallbackData(getServiceState());

        return button;
    }

    default List<InlineKeyboardButton> getKeyboardButtons(final TelegramUser user, final Update update){
        return getSubServices().stream().map(telegramService -> {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setCallbackData(telegramService.getServiceState());
            button.setText(telegramService.getServiceLabel());
            return button;
        }).collect(Collectors.toList());
    }

    default String getParseMode(){
        return ParseMode.HTML;
    }
}
