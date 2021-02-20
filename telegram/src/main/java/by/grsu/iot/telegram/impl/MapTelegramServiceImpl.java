package by.grsu.iot.telegram.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.interf.TelegramUserService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.MapTelegramService;
import by.grsu.iot.telegram.interf.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class MapTelegramServiceImpl implements MapTelegramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapTelegramServiceImpl.class);

    @Lazy
    @Autowired
    private List<? extends TelegramService> list;

    @Autowired
    private TelegramUserService telegramUserService;

    @Override
    public TelegramResponse handleReceivedUpdateByTelegramService(TelegramUser user, Update update) {
        for (TelegramService service : list){
            if(user.peekState().equals(service.getServiceState())){
                return service.handleReceivedUpdate(user, update);
            }
        }

        throw new IllegalArgumentException("Not found bean with such state {" + user.peekState() + "}");
    }

    @Override
    public TelegramResponse getBack(TelegramUser user, Update update) {
        user.popState();
        user = telegramUserService.update(user);
        return handleReceivedUpdateByTelegramService(user, update);
    }

    @Override
    public TelegramResponse getWelcomeTelegramResponseByTelegramService(TelegramUser user, Update update) {
        for (TelegramService service : list){
            if(user.peekState().equals(service.getServiceState())){
                return service.getWelcomeTelegramResponse(user, update);
            }
        }

        throw new IllegalArgumentException("Not found bean with such state {" + user.peekState() + "}");
    }
}
