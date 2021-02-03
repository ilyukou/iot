package by.grsu.iot.api.controller;

import by.grsu.iot.api.dto.AuthenticationRequest;
import by.grsu.iot.api.dto.AuthenticationUser;
import by.grsu.iot.api.dto.RegistrationRequest;
import by.grsu.iot.api.dto.RegistrationResponse;
import by.grsu.iot.api.security.jwt.JwtTokenProvider;
import by.grsu.iot.api.service.interf.UserService;
import by.grsu.iot.api.validator.RegistrationRequestValidator;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.ResponseEntity.ok;

/**
 * This controller allows sing in and sing up {@link User}.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RegistrationRequestValidator registrationRequestValidator;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService,
                          RegistrationRequestValidator registrationRequestValidator) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.registrationRequestValidator = registrationRequestValidator;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationUser> signIn(@RequestBody AuthenticationRequest data) {

        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
            authenticationManager.authenticate(authReq);

            User user = userService.getByUsername(data.getUsername());

            if (user == null) {
                throw new UsernameNotFoundException("Username " + data.getUsername() + "not found");
            }

            String token = jwtTokenProvider.createToken(data.getUsername(), user.getRoles());

            return ok(new AuthenticationUser(data.getUsername(), token, user.getId()));
        } catch (AuthenticationException e) {
            throw new EntityNotFoundException("Invalid username/password supplied");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<RegistrationResponse> singUp(@RequestBody RegistrationRequest data) {

//        final DataBinder dataBinder = new DataBinder(data);
//        dataBinder.addValidators(registrationRequestValidator);
//        dataBinder.validate();
//
//        if (dataBinder.getBindingResult().hasErrors()) {
//            ExceptionUtil.throwException(dataBinder.getBindingResult());
//        }

        User user = EntityFactory.createUser();

        user.setEmail(EntityFactory.createEmail(data.getEmail()));
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());

        user = userService.create(user);

        return ok(new RegistrationResponse(user));
    }
}
