package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.TelegramUser;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.interf.TelegramUserRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.TelegramUserJpaRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TelegramUserRepositoryImpl implements TelegramUserRepository {

    private static final String DEFAULT_CREATED_STATE = "start";

    private final TelegramUserJpaRepository telegramUserJpaRepository;
    private final UserRepository userRepository;

    public TelegramUserRepositoryImpl(TelegramUserJpaRepository telegramUserJpaRepository,
                                      UserRepository userRepository) {
        this.telegramUserJpaRepository = telegramUserJpaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TelegramUser create(final org.telegram.telegrambots.meta.api.objects.User telegramRawUser) {

        TelegramUser telegramUser = new TelegramUser(SerializationUtils.clone(telegramRawUser));

        telegramUser.addState(DEFAULT_CREATED_STATE);

        telegramUser = telegramUserJpaRepository.save(telegramUser);

        return telegramUser;
    }

    @Override
    public TelegramUser get(final Integer userId) {
        return telegramUserJpaRepository.findTelegramUserByUserId(userId).orElse(null);
    }

    @Override
    public TelegramUser update(final TelegramUser telegramUser) {
        return telegramUserJpaRepository.save(telegramUser);
    }

    @Override
    public boolean delete(final TelegramUser telegramUser) {
        TelegramUser tlgrmUser = get(telegramUser.getUserId());

        tlgrmUser.setUser(null);
        update(tlgrmUser);

        User user = tlgrmUser.getUser();
        user.setTelegramUser(null);
        userRepository.update(user);

        telegramUserJpaRepository.delete(tlgrmUser);

        return true;
    }

    @Override
    public boolean isExist(final Integer userId) {
        return telegramUserJpaRepository.existsByUserId(userId);
    }
}
