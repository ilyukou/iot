package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.async.EmailCode;
import by.grsu.iot.model.async.EmailCodeType;
import by.grsu.iot.model.dto.email.SendEmailCodeRequest;
import by.grsu.iot.model.exception.BadRequestApplicationException;
import by.grsu.iot.model.sql.Email;
import by.grsu.iot.producer.interf.EmailCodeProducer;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.service.interf.crud.EmailCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EmailCrudServiceImpl implements EmailCrudService {

    private final EmailRepository emailRepository;
    private final EmailCodeProducer emailCodeProducer;

    public EmailCrudServiceImpl(
            EmailRepository emailRepository,
            EmailCodeProducer emailCodeProducer
    ) {
        this.emailRepository = emailRepository;
        this.emailCodeProducer = emailCodeProducer;
    }

    @Override
    public Email getByAddress(String address) {
        return emailRepository.findByAddress(address);
    }

    @Override
    public Email create(Email email) {
        return emailRepository.create(email);
    }

    @Override
    public Integer changeEmailAddress(String username, String address) {
        if (emailRepository.isExist(address)) {
            throw new BadRequestApplicationException("email", "User with such email exist");
        }

        Email email = emailRepository.changeAddress(username, address);

        emailCodeProducer.produce(new EmailCode(address, email.getCode(), EmailCodeType.CHANGE_EMAIL));

        return email.getCode();
    }

    @Override
    public void sendEmailCode(SendEmailCodeRequest data) {
//        if (emailRepository.isExist(data.getAddress())) {
//            throw new BadRequestApplicationException("email", "User with such email exist");
//        }

        Integer code = emailRepository.createCode(data.getAddress());

        emailCodeProducer.produce(new EmailCode(data.getAddress(), code, data.getType()));
    }
}
