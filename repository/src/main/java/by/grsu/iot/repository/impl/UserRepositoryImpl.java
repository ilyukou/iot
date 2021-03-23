package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.UserJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final EmailRepository emailRepository;

    public UserRepositoryImpl(
            UserJpaRepository userJpaRepository,
            EmailRepository emailRepository
    ) {
        this.userJpaRepository = userJpaRepository;
        this.emailRepository = emailRepository;
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
        return userJpaRepository.findUsername(userId);
    }

    @Override
    public Long getUserId(String username) {
        return userJpaRepository.findUserId(username);
    }
}
