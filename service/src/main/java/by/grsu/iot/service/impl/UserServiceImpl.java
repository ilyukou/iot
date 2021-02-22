package by.grsu.iot.service.impl;

import by.grsu.iot.model.api.RegistrationRequest;
import by.grsu.iot.service.activemq.EntityProducer;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.interf.UserService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.validation.factory.DataBinderFactory;
import by.grsu.iot.service.validation.validator.AuthenticationRequestValidator;
import by.grsu.iot.service.validation.validator.RegistrationRequestValidator;
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
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityProducer entityProducer;
    private final EntityFactory entityFactory;
    private final AuthenticationManager authenticationManager;
    private final DataBinderFactory dataBinderFactory;

    @Autowired
    public UserServiceImpl(EmailRepository emailRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, EntityProducer entityProducer,
                           EntityFactory entityFactory, AuthenticationManager authenticationManager,
                           DataBinderFactory dataBinderFactory) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityProducer = entityProducer;
        this.entityFactory = entityFactory;
        this.authenticationManager = authenticationManager;
        this.dataBinderFactory = dataBinderFactory;
    }

    @Override
    public User create(final RegistrationRequest registrationRequest) {
        DataBinder dataBinder = dataBinderFactory.createDataBinder(registrationRequest);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ExceptionUtil.throwException(dataBinder.getBindingResult());
        }

        User u = entityFactory.createUser();
        u.setEmail(entityFactory.createEmail(registrationRequest.getEmail()));
        u.setUsername(registrationRequest.getUsername());
        u.setPassword(registrationRequest.getPassword());

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
