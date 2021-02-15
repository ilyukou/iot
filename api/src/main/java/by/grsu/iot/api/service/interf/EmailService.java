package by.grsu.iot.api.service.interf;


import by.grsu.iot.model.sql.Email;

public interface EmailService {

    Email findByAddress(String address);

    Email create(Email email);

    boolean isExist(String address);
}
