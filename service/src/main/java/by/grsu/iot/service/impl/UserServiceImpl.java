package by.grsu.iot.service.impl;

import by.grsu.iot.service.activemq.EntityProducer;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.UserService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.ResponseEntity.badRequest;

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
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(EmailRepository emailRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,
                           EntityProducer entityProducer, EntityFactory entityFactory, AuthenticationManager authenticationManager) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityProducer = entityProducer;
        this.entityFactory = entityFactory;
        this.authenticationManager = authenticationManager;
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

    @Override
    public User getUser(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken req
                    = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(req);

            User user = getByUsername(username);

            return user;

        } catch (AuthenticationException e) {
            return null;
        }
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }
}
