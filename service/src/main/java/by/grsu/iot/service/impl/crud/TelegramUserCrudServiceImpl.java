package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.repository.interf.TelegramUserRepository;
import by.grsu.iot.service.interf.crud.TelegramUserCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TelegramUserCrudServiceImpl implements TelegramUserCrudService {

    private final TelegramUserRepository telegramUserRepository;

    public TelegramUserCrudServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public TelegramUser create(org.telegram.telegrambots.meta.api.objects.User telegramRawUser) {
        return telegramUserRepository.create(telegramRawUser);
    }

    @Override
    public TelegramUser update(TelegramUser telegramUser) {
        return telegramUserRepository.update(telegramUser);
    }

    @Override
    public boolean isExist(Integer userId) {
        return telegramUserRepository.isExist(userId);
    }

    @Override
    public TelegramUser get(Integer userId) {
        return telegramUserRepository.get(userId);
    }

    @Override
    public void delete(TelegramUser user) {
        telegramUserRepository.delete(user);
    }
}
