package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.service.interf.crud.EmailCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EmailCrudServiceImpl implements EmailCrudService {

    private final EmailRepository emailRepository;

    public EmailCrudServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public Email getByAddress(String address) {
        return emailRepository.findByAddress(address);
    }

    @Override
    public Email create(Email email) {
        return emailRepository.create(email);
    }
}
