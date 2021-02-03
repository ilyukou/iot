package by.grsu.iot.api.service.impl;

import by.grsu.iot.api.service.activemq.EntityProducer;
import by.grsu.iot.api.service.interf.EmailService;
import by.grsu.iot.api.service.interf.UserService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.User;

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityProducer entityProducer;

    @Autowired
    public UserServiceImpl(EmailService emailService, UserRepository userRepository, PasswordEncoder passwordEncoder, EntityProducer entityProducer) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityProducer = entityProducer;
    }

    @Override
    public User create(User user) {

        if (emailService.isExist(user.getEmail().getAddress()) || userRepository.isExistByUsername(user.getUsername())) {
            String ms = "User with such username or email exist";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        user = userRepository.create(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        entityProducer.sendMessage(user, ActActiveMQ.CREATE);

        return user;
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public boolean isUserHasProjectByProjectId(User user, Long projectId) {
        for (Project p : user.getProjects()) {
            if (p.getId().equals(projectId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void deleteById(Long id) {
        User user = getById(id);

        userRepository.disableUserByUserId(id);

        entityProducer.sendMessage(user, ActActiveMQ.DELETE);
    }

    @Override
    public boolean isExist(Long id) {
        return userRepository.isExist(id);
    }

    @Override
    public User update(User user) {
        user = userRepository.update(user);

        entityProducer.sendMessage(user, ActActiveMQ.CREATE);

        return user;
    }

    @Override
    public void confirmUser(String verificationCode) {
        Email email = emailService.findByCode(verificationCode);

        if (email == null) {
            throw new EntityNotFoundException("Email not found with such verification code");
        }

        email.setStatus(Status.ACTIVE);
        emailService.update(email);

        User user = email.getUser();

        user.setStatus(Status.ACTIVE);
        update(user);
    }
}
