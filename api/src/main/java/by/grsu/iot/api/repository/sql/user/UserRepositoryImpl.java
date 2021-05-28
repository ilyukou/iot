package by.grsu.iot.api.repository.sql.user;

import by.grsu.iot.api.model.sql.Email;
import by.grsu.iot.api.model.sql.Role;
import by.grsu.iot.api.model.sql.RoleType;
import by.grsu.iot.api.model.sql.User;
import by.grsu.iot.api.repository.sql.jpa.UserJpaRepository;
import by.grsu.iot.api.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final EmailRepository emailRepository;
    private final RoleRepository roleRepository;

    public UserRepositoryImpl(
            UserJpaRepository userJpaRepository,
            EmailRepository emailRepository,
            RoleRepository roleRepository) {
        this.userJpaRepository = userJpaRepository;
        this.emailRepository = emailRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(final User user) {
        User u = SerializationUtils.clone(user);

        // Create Email entity
        Email email = emailRepository.create(u.getEmail());

        u.setEmail(email);

        Date date = TimeUtil.getCurrentDate();
        u.setUpdated(date);
        u.setCreated(date);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getRoleOrCreate(RoleType.User));
        user.setRoles(roles);

        // Save User with Email and Roles
        u = userJpaRepository.save(u);

        // Save User in Email entity
        email.setUser(u);
        emailRepository.update(email);

        return u;
    }

    @Override
    public User getById(final Long id) {
        return userJpaRepository.findById(id).orElse(null);
    }

    @Override
    public User update(final User user) {
        User u = SerializationUtils.clone(user);

        u.setUpdated(TimeUtil.getCurrentDate());
        return userJpaRepository.save(u);
    }

    @Override
    public User getByUsername(final String username) {
        return userJpaRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean isExistByUsername(final String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public String findUsername(Long userId) {
        return userJpaRepository.findUsername(userId).orElse(null);
    }

    @Override
    public Long getUserId(String username) {
        return userJpaRepository.findUserId(username).orElse(null);
    }

    @Override
    public Long getEmailId(String username) {
        return userJpaRepository.getEmailId(username);
    }
}
