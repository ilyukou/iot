package by.grsu.iot.telegram.telegramService.impl;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.DeviceService;
import by.grsu.iot.telegram.domain.TelegramResponse;
import by.grsu.iot.telegram.interf.TelegramService;
import by.grsu.iot.telegram.telegramService.interf.BackTelegramService;
import by.grsu.iot.telegram.telegramService.interf.DeviceTelegramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DeviceTelegramServiceImpl implements DeviceTelegramService {

    @Value("${by.grsu.iot.telegram.bot.device.state}")
    private String STATE;

    @Value("${by.grsu.iot.telegram.bot.device.label}")
    private String LABEL;

    private final BackTelegramService backTelegramService;
    private final DeviceService deviceService;

    public DeviceTelegramServiceImpl(
            BackTelegramService backTelegramService,
            DeviceService deviceService) {
        this.backTelegramService = backTelegramService;
        this.deviceService = deviceService;
    }

    @Override
    public TelegramResponse handleReceivedUpdate(TelegramUser user, Update update) {
        if(!update.hasCallbackQuery()){
            throw new BadRequestException();
        }

        if (update.getCallbackQuery().getData().equals("back")){
            return backTelegramService.getWelcomeTelegramResponse(user, update);
        }

        if (update.getCallbackQuery().getData().contains("thing")){
            String[] data = update.getCallbackQuery().getData().split(" ");

            if(data.length != 3){
                throw new BadRequestException();
            }

            Device device = deviceService.getById(Long.valueOf(data[2]), user.getUser().getUsername());
            device.setState(data[2]);

            deviceService.update(device);

            return getWelcomeEditMessageTextTelegramResponse(user, update);
        }

        throw new BadRequestException();
    }

    @Override
    public String getMessageText(Update update) {
        return null;
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
    public List<InlineKeyboardButton> getKeyboardButtons(TelegramUser user, Update update) {
        if(!update.hasCallbackQuery() || !update.getCallbackQuery().getData().contains("thing")){
            throw new BadRequestException();
        }

        String[] data = update.getCallbackQuery().getData().split(" ");

        if(data.length != 3){
            throw new BadRequestException();
        }

        List<InlineKeyboardButton> buttons = new ArrayList<>();

        Device device = deviceService.getById(Long.valueOf(data[2]), user.getUser().getUsername());

        for (String state : device.getStates()){

            InlineKeyboardButton button = new InlineKeyboardButton();

            if(state.equals(device.getState())){
                button.setCallbackData("thing " + device.getId() + " " + device.getState());
                button.setText("✔" + device.getState());
            } else {
                button.setCallbackData("thing " + device.getId() + " " + device.getState());
                button.setText("⬜" + device.getState());
            }

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
        return Arrays.asList(backTelegramService);
    }

    @Override
    public TelegramResponse refresh(TelegramUser user, Update update) {
        return null;
    }
}
