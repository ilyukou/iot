package by.grsu.iot.api.controller;

import javax.validation.Valid;
import by.grsu.iot.model.api.AuthenticationRequest;
import by.grsu.iot.api.dto.AuthenticationUser;
import by.grsu.iot.model.api.RegistrationRequest;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.security.jwt.JwtProperties;
import by.grsu.iot.service.security.jwt.JwtTokenProvider;
import by.grsu.iot.service.interf.UserService;
import by.grsu.iot.service.validation.validator.AuthenticationRequestValidator;
import by.grsu.iot.service.validation.validator.RegistrationRequestValidator;
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
    private final JwtProperties jwtProperties;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserService userService,
            JwtProperties jwtProperties
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.jwtProperties = jwtProperties;
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

            return ok(new AuthenticationUser(data.getUsername(), token, jwtProperties.getValidityInMs()));
        } catch (AuthenticationException e) {
            return badRequest().build();
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> singUp(@RequestBody RegistrationRequest data) {

        userService.create(data);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
