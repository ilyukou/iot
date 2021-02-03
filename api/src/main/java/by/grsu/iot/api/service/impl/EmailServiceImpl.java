package by.grsu.iot.api.service.impl;

import by.grsu.iot.api.service.interf.EmailService;
import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.interf.EmailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    public EmailServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public Email findByAddress(String address) {
        return emailRepository.findByAddress(address);
    }

    @Override
    public Email create(Email email) {
        return emailRepository.create(email);
    }

    @Override
    public Email getById(Long id) {
        return emailRepository.getById(id);
    }

    @Override
    public Email update(Email email) {
        return emailRepository.update(email);
    }

    @Override
    public boolean isExist(String address) {
        return findByAddress(address) != null;
    }

    @Override
    public Email findByCode(String verificationCode) {
        return emailRepository.findByVerificationToken(verificationCode);
    }
}
