package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.model.sql.Status;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.RoleRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.UserJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final RoleType DEFAULT_ROLE = RoleType.User;
    private final UserJpaRepository userJpaRepository;
    private final EmailRepository emailRepository;
    private final EntityFactory entityFactory;
    private final TimeUtil timeUtil;
    private final RoleRepository roleRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository,
                              RoleRepository roleRepository,
                              EmailRepository emailRepository,
                              EntityFactory entityFactory,
                              TimeUtil timeUtil) {
        this.userJpaRepository = userJpaRepository;
        this.roleRepository = roleRepository;
        this.emailRepository = emailRepository;
        this.entityFactory = entityFactory;
        this.timeUtil = timeUtil;
    }

    @Override
    public User create(final User user) {
        User u = SerializationUtils.clone(user);

        Date date = timeUtil.getCurrentDate();

        // Create Email entity
        Email email = emailRepository.create(entityFactory.createEmail(u.getEmail().getAddress()));

        u.setEmail(email);
        u.setRoles(Collections.singletonList(roleRepository.getRoleOrCreate(DEFAULT_ROLE)));
        u.setCreated(date);
        u.setUpdated(date);

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

        u.setUpdated(timeUtil.getCurrentDate());
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
}
