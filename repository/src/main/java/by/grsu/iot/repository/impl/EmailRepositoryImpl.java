package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.EmailJpaRepository;
import by.grsu.iot.util.service.NumberUtil;
import by.grsu.iot.util.service.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Transactional
@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private static final String EMAIL_CODE_LENGTH = "by.grsu.iot.repository.email.code.length";

    private final Environment environment;
    private final EmailJpaRepository emailJpaRepository;
    private final UserRepository userRepository;

    public EmailRepositoryImpl(
            Environment environment, EmailJpaRepository emailJpaRepository,
            @Lazy UserRepository userRepository // cycle dependency
    ) {
        this.environment = environment;
        this.emailJpaRepository = emailJpaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Email findByAddress(final String address) {
        return emailJpaRepository.findEmailByAddress(address).orElse(null);
    }

    @Override
    public Email create(final Email email) {
        Email e = SerializationUtils.clone(email);

        Date date = TimeUtil.getCurrentDate();
        e.setUpdated(date);
        e.setCreated(date);
        return emailJpaRepository.save(e);
    }

    @Override
    public Email getById(final Long id) {
        return emailJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Email update(final Email email) {
        Email e = SerializationUtils.clone(email);

        e.setUpdated(TimeUtil.getCurrentDate());
        return emailJpaRepository.save(e);
    }

    @Override
    public boolean isExist(String address) {
        return emailJpaRepository.findEmailByAddress(address).isPresent();
    }

    @Override
    public Email changeAddress(String username, String address) {
        Email email = getById(userRepository.getEmailId(username));
        email.setAddress(address);
        email.setCode(NumberUtil.generateNumber(Long.parseLong(Objects.requireNonNull(environment.getProperty(EMAIL_CODE_LENGTH)))));

        return update(email);
    }

    @Override
    public Integer getCode(String username) {
        return emailJpaRepository.getEmailCode(userRepository.getEmailId(username));
    }

    @Override
    public Integer createCode(String address) {
        Integer code = NumberUtil.generateNumber(Long.parseLong(Objects.requireNonNull(environment.getProperty(EMAIL_CODE_LENGTH))));

        emailJpaRepository.setCode(address, code);

        return code;
    }
}
