package by.grsu.iot.api.service.interf;


import by.grsu.iot.model.sql.User;

public interface UserService {

    User create(User user);

    User getById(Long id);

    User getByUsername(String username);

    boolean isUserHasProjectByProjectId(User user, Long projectId);

    void deleteById(Long id);

    boolean isExist(Long id);

    User update(User user);

    void confirmUser(String verificationCode);
}
