package by.grsu.iot.telegram.interf;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.template.InlineKeyboardMarkupTemplate;
import by.grsu.iot.telegram.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface TelegramService {

    default TelegramResponse handleReceivedUpdate(TelegramUser user, Update update){
        if (isCallbackQueryHasTransition(user, update)){
            TelegramService service = getTransitionService(user, update);
            user.addState(service.getServiceState());
            return service.getWelcomeTelegramResponse(user, update);
        }

        throw new BadRequestException();
    }

    default TelegramResponse getWelcomeTelegramResponse(final TelegramUser user, final Update update){
        if (update.hasCallbackQuery()){
            return new TelegramResponse(getEditMessage(user, update), update);
        }

        return new TelegramResponse(getSendMessage(user, update));
    }

    String getMessageText(Update update);

    default String getMessageTextAndRefresh(Update update){
        return getMessageText(update) +
                "\n" +
                "\n" +
                new Date().toString();
    }

    String getServiceLabel();

    String getServiceState();

    List<? extends TelegramService> getSubServices();

    default boolean isCallbackQueryHasTransition(final TelegramUser user, final Update update){
        if (!update.hasCallbackQuery()){
            return false;
        }

        for (TelegramService service : getSubServices()){
            if(service.getServiceState().equals(update.getCallbackQuery().getData())){
                return true;
            }
        }

        return false;
    }

    default TelegramService getTransitionService(final TelegramUser user, final Update update){
        for (TelegramService service : getSubServices()){
            if(service.getServiceState().equals(update.getCallbackQuery().getData())){
                return service;
            }
        }
        return null;
    }

    default byte getMaxColumnInRow(){
        return 1;
    }

    default EditMessageText getEditMessage(final TelegramUser user, final Update update){
        EditMessageText editMessageText = new EditMessageText();

        editMessageText.setChatId(String.valueOf(user.getUserId()));
        editMessageText.setText(getMessageTextAndRefresh(update));
        editMessageText.setReplyMarkup(InlineKeyboardMarkupTemplate.getMarkup(
                getKeyboardButtons(user, update),
                getMaxColumnInRow()
        ));

        editMessageText.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setParseMode(getParseMode());

        return editMessageText;
    }

    default SendMessage getSendMessage(final TelegramUser user, final Update update){
        SendMessage sendMessage = new SendMessage();

        sendMessage.setReplyMarkup(InlineKeyboardMarkupTemplate.getMarkup(
                getKeyboardButtons(user, update),
                getMaxColumnInRow()
        ));
        sendMessage.setChatId(String.valueOf(UpdateUtil.getChatId(update)));
        sendMessage.setText(getMessageTextAndRefresh(update));
        sendMessage.setParseMode(ParseMode.HTML);

        return sendMessage;
    }

    default InlineKeyboardButton getButton(){
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(getServiceLabel());
        button.setCallbackData(getServiceState());

        return button;
    }

    default List<InlineKeyboardButton> getKeyboardButtons(final TelegramUser user, final Update update){
        return getSubServices().stream().map(TelegramService::getButton).collect(Collectors.toList());
    }

    default String getParseMode(){
        return ParseMode.HTML;
    }
}
