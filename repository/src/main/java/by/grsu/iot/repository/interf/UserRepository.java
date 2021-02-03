package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.model.sql.User;

public interface UserRepository {

    User create(User user);

    User getById(Long id);

    boolean disableUserByUserId(Long userId);

    boolean isExist(Long id);

    User update(User user);

    User getByUsername(String username);

    boolean isExistByUsername(String username);

    RoleType getUserRole(String username);
}

