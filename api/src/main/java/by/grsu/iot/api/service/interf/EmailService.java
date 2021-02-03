package by.grsu.iot.api.service.interf;


import by.grsu.iot.model.sql.Email;

public interface EmailService {

    Email findByAddress(String address);

    Email create(Email email);

    Email getById(Long id);

    Email update(Email email);

    boolean isExist(String address);

    Email findByCode(String verificationCode);
}
