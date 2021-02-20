package by.grsu.iot.telegram.impl;

import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.ErrorService;
import by.grsu.iot.telegram.util.UpdateUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ErrorServiceImpl implements ErrorService {

    @Override
    public TelegramResponse getBadRequestMessage(String message, Update update) {

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(String.valueOf(UpdateUtil.getChatId(update)));
        sendMessage.setText(message);
        sendMessage.setParseMode(ParseMode.HTML);

        return new TelegramResponse(sendMessage);
    }
}
