package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.jpa.EmailJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final EmailJpaRepository emailJpaRepository;

    public EmailRepositoryImpl(EmailJpaRepository emailJpaRepository) {
        this.emailJpaRepository = emailJpaRepository;
    }

    @Override
    public Email findByAddress(String address) {
        return emailJpaRepository.findEmailByAddress(address).orElse(null);
    }

    @Override
    public Email create(Email email) {
        return update(email);
    }

    @Override
    public Email getById(Long id) {
        return emailJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Email update(Email email) {
        return emailJpaRepository.save(email);
    }

    @Override
    public Email findByVerificationToken(String verificationToken) {
        return emailJpaRepository.findEmailByCode(verificationToken).orElse(null);
    }
}
