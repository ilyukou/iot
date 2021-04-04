package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.async.EmailCode;
import by.grsu.iot.model.async.EmailCodeType;
import by.grsu.iot.model.dto.user.*;
import by.grsu.iot.model.exception.BadRequestApplicationException;
import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.exception.NotActiveEntityApplicationException;
import by.grsu.iot.model.sql.Email;
import by.grsu.iot.model.sql.Status;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.producer.interf.EmailCodeProducer;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.security.jwt.JwtProperties;
import by.grsu.iot.security.jwt.JwtTokenProvider;
import by.grsu.iot.service.interf.crud.EmailCrudService;
import by.grsu.iot.service.interf.crud.UserCrudService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCrudServiceImpl implements UserCrudService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final EmailCodeProducer emailCodeProducer;
    private final EmailCrudService emailCrudService;

    public UserCrudServiceImpl(
            EmailRepository emailRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            JwtProperties jwtProperties,
            EmailCodeProducer emailCodeProducer,
            EmailCrudService emailCrudService
    ) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.emailCodeProducer = emailCodeProducer;
        this.emailCrudService = emailCrudService;
    }

    @Override
    public User create(final RegistrationRequest registrationRequest) {

        if (emailRepository.isExist(registrationRequest.getEmail())) {
            throw new BadRequestApplicationException("email", "User with such email exist");
        }

        if (userRepository.isExistByUsername(registrationRequest.getUsername())) {
            throw new BadRequestApplicationException("username", "User with such username exist");
        }

        User u = EntityFactory.createUser(registrationRequest.getEmail());

        u.setUsername(registrationRequest.getUsername());
        u.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        u = userRepository.create(u);

        emailCodeProducer.produce(
                new EmailCode(u.getEmail().getAddress(), u.getEmail().getCode(), EmailCodeType.CREATE_ACCOUNT));

        return u;
    }

    @Override
    public AuthenticationUser authenticate(final AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        authenticationManager.authenticate(authReq);

        User user = getByUsername(authenticationRequest.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("Username " + authenticationRequest.getUsername() + "not found");
        }

        if (!user.isActive()){
            throw new NotActiveEntityApplicationException("User not active");
        }

        String token = jwtTokenProvider.createToken(authenticationRequest.getUsername(), user.getRoles());

        return new AuthenticationUser(authenticationRequest.getUsername(), token, jwtProperties.getValidityInMs());
    }

    @Override
    public User getByUsername(final String username) {
        User user = userRepository.getByUsername(username);

        if (user == null) {
            throw new EntityNotFoundApplicationException("User does not found with such username={" + username + "}");
        }

        return user;
    }

    @Override
    public void activateUser(ActivateUser data) {
        User user = getByUsername(data.getUsername());

        if (!user.getEmail().getCode().equals(data.getCode())){
            throw new BadRequestApplicationException("code", "Bad code");
        }

        Email email = user.getEmail();
        email.setCode(null);
        emailRepository.update(email);

        user.setStatus(Status.ACTIVE);
        userRepository.update(user);
    }

    @Override
    public void update(String username, UserUpdateForm form) {
        User user = getByUsername(username);

        int i = 0;

        if (form.getEmail() != null){
            emailCrudService.changeEmailAddress(username, form.getEmail());
            user.setStatus(Status.NOT_ACTIVE);
            i++;
        }

        if (form.getUsername() != null){
            if (userRepository.isExistByUsername(form.getUsername())) {
                throw new BadRequestApplicationException("username", "User with such username exist");
            }

            user.setUsername(form.getUsername());
            i++;
        }

        if (form.getPassword() != null){
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            i++;
        }

        if (i > 0){
            userRepository.update(user);
        }
    }

    @Override
    public void restorePassword(RestorePasswordForm data) {
        Integer code = emailRepository.getCode(data.getUsername());

        if (code != null && !code.equals(data.getCode())){
            throw new BadRequestApplicationException("code", "Bad code");
        }

        User user = getByUsername(data.getUsername());

        user.setPassword(passwordEncoder.encode(data.getPassword()));

        userRepository.update(user);
    }
}
