package by.grsu.iot.telegram.util;

import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.template.InlineKeyboardMarkupTemplate;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class UpdateUtil {

    public static Integer getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        if (update.hasEditedMessage()) {
            return update.getEditedMessage().getFrom().getId();
        }
        return null; // FIXME
    }

    public static String getText(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getText();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        }

        return null;
    }

//    public static EditMessageText builder(Integer userId, String text, InlineKeyboardMarkup markup, String){
//        EditMessageText editMessageText = new EditMessageText();
//
//        editMessageText.setChatId(String.valueOf(userId));
//        editMessageText.setText(text);
//        editMessageText.setReplyMarkup(markup);
//
//        editMessageText.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());
//        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
//        editMessageText.setParseMode(getParseMode());
//
//        return new TelegramResponse(editMessageText, update);
//    }
}
