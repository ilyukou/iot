package by.grsu.iot.service.interf.crud;


import by.grsu.iot.model.sql.Email;

public interface EmailCrudService {

    Email findByAddress(String address);

    Email create(Email email);

    boolean isExist(String address);
}
