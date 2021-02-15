package by.grsu.iot.api.service.impl;

import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.service.activemq.EntityProducer;
import by.grsu.iot.api.service.interf.EmailService;
import by.grsu.iot.api.service.interf.UserService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.apache.commons.lang3.SerializationUtils;
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

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityProducer entityProducer;
    private final EntityFactory entityFactory;

    @Autowired
    public UserServiceImpl(EmailRepository emailRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
                           EntityProducer entityProducer, EntityFactory entityFactory) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityProducer = entityProducer;
        this.entityFactory = entityFactory;
    }

    @Override
    public User create(final User user) {
        User u = SerializationUtils.clone(user);

        if(emailRepository.isExist(u.getEmail().getAddress())){
            throw new BadRequestException("email", "User with such email exist");
        }

        if (userRepository.isExistByUsername(u.getUsername())) {
            throw new BadRequestException("username", "User with such username exist");
        }

        u.setEmail(entityFactory.createEmail(u.getEmail().getAddress()));
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        u = userRepository.create(u);


        entityProducer.sendMessage(u, ActActiveMQ.CREATE);

        return u;
    }

    @Override
    public User getByUsername(final String username) {
        User user = userRepository.getByUsername(username);

        if(user == null){
            throw new EntityNotFoundException("User does not found with such username={" + username + "}");
        }

        return user;
    }
}
