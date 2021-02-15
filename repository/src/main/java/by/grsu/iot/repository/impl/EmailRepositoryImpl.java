package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.jpa.EmailJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final EmailJpaRepository emailJpaRepository;
    private final TimeUtil timeUtil;

    public EmailRepositoryImpl(EmailJpaRepository emailJpaRepository, TimeUtil timeUtil) {
        this.emailJpaRepository = emailJpaRepository;
        this.timeUtil = timeUtil;
    }

    @Override
    public Email findByAddress(final String address) {
        return emailJpaRepository.findEmailByAddress(address).orElse(null);
    }

    @Override
    public Email create(final Email email) {
        Email e = SerializationUtils.clone(email);

        Date date = timeUtil.getCurrentDate();
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

        e.setUpdated(timeUtil.getCurrentDate());
        return emailJpaRepository.save(e);
    }

    @Override
    public boolean isExist(String address) {
        return emailJpaRepository.existsByAddress(address);
    }
}
