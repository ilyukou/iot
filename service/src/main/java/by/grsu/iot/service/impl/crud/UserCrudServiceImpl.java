package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.EmailRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.domain.AuthenticationRequest;
import by.grsu.iot.service.domain.AuthenticationUser;
import by.grsu.iot.service.domain.RegistrationRequest;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.interf.crud.UserCrudService;
import by.grsu.iot.service.security.jwt.JwtProperties;
import by.grsu.iot.service.security.jwt.JwtTokenProvider;
import by.grsu.iot.service.validation.factory.DataBinderFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;

import javax.persistence.EntityNotFoundException;

@Service
public class UserCrudServiceImpl implements UserCrudService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityFactory entityFactory;
    private final AuthenticationManager authenticationManager;
    private final DataBinderFactory dataBinderFactory;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    public UserCrudServiceImpl(
            EmailRepository emailRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EntityFactory entityFactory,
            AuthenticationManager authenticationManager,
            DataBinderFactory dataBinderFactory,
            JwtTokenProvider jwtTokenProvider,
            JwtProperties jwtProperties
    ) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityFactory = entityFactory;
        this.authenticationManager = authenticationManager;
        this.dataBinderFactory = dataBinderFactory;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public User create(final RegistrationRequest registrationRequest) {
        DataBinder dataBinder = dataBinderFactory.createDataBinder(registrationRequest);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ExceptionUtil.throwException(dataBinder.getBindingResult());
        }

        if (emailRepository.isExist(registrationRequest.getEmail())) {
            throw new BadRequestException("email", "User with such email exist");
        }

        if (userRepository.isExistByUsername(registrationRequest.getUsername())) {
            throw new BadRequestException("username", "User with such username exist");
        }

        User u = entityFactory.createUser(registrationRequest.getEmail());

        u.setUsername(registrationRequest.getUsername());
        u.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        u = userRepository.create(u);

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

        String token = jwtTokenProvider.createToken(authenticationRequest.getUsername(), user.getRoles());

        return new AuthenticationUser(authenticationRequest.getUsername(), token, jwtProperties.getValidityInMs());
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
    public User update(final User user) {
        return userRepository.update(user);
    }
}
