package by.grsu.iot.repository.interf;


import by.grsu.iot.model.sql.Email;

public interface EmailRepository {

    Email findByAddress(String address);

    Email create(Email email);

    Email getById(Long id);

    Email update(Email email);

    Email findByVerificationToken(String verificationToken);
}
