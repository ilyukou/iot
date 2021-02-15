package by.grsu.iot.api.controller;

import javax.validation.Valid;
import by.grsu.iot.api.dto.AuthenticationRequest;
import by.grsu.iot.api.dto.AuthenticationUser;
import by.grsu.iot.api.dto.RegistrationRequest;
import by.grsu.iot.api.exception.ExceptionUtil;
import by.grsu.iot.api.security.jwt.JwtProperties;
import by.grsu.iot.api.security.jwt.JwtTokenProvider;
import by.grsu.iot.api.service.interf.UserService;
import by.grsu.iot.api.validation.validator.AuthenticationRequestValidator;
import by.grsu.iot.api.validation.validator.RegistrationRequestValidator;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

/**
 * This controller allows sing in and sing up {@link User}.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RegistrationRequestValidator registrationRequestValidator;
    private final AuthenticationRequestValidator authenticationRequestValidator;
    private final JwtProperties jwtProperties;
    private final EntityFactory entityFactory;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService,
                          RegistrationRequestValidator registrationRequestValidator, AuthenticationRequestValidator authenticationRequestValidator, JwtProperties jwtProperties, EntityFactory entityFactory) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.registrationRequestValidator = registrationRequestValidator;
        this.authenticationRequestValidator = authenticationRequestValidator;
        this.jwtProperties = jwtProperties;
        this.entityFactory = entityFactory;
    }

    @InitBinder("authenticationRequest")
    protected void initAuthenticationRequestBinder(WebDataBinder binder) {
        binder.setValidator(authenticationRequestValidator);
    }

    @InitBinder("registrationRequest")
    protected void initRegistrationRequestBinder(WebDataBinder binder) {
        binder.setValidator(registrationRequestValidator);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationUser> signIn(@RequestBody @Valid AuthenticationRequest data, BindingResult result) {

        if (result.hasErrors()) {
            ExceptionUtil.throwException(result);
        }

        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
            authenticationManager.authenticate(authReq);

            User user = userService.getByUsername(data.getUsername());

            if (user == null) {
                throw new UsernameNotFoundException("Username " + data.getUsername() + "not found");
            }

            String token = jwtTokenProvider.createToken(data.getUsername(), user.getRoles());

            return ok(new AuthenticationUser(data.getUsername(), token, jwtProperties.getValidityInMs()));
        } catch (AuthenticationException e) {
            return badRequest().build();
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> singUp(@RequestBody @Valid RegistrationRequest data, BindingResult result) {
        if (result.hasErrors()) {
            ExceptionUtil.throwException(result);
        }

        User user = entityFactory.createUser();

        user.setEmail(entityFactory.createEmail(data.getEmail()));
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());

        userService.create(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
