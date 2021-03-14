package by.grsu.iot.api.controller;

import by.grsu.iot.service.domain.request.user.AuthenticationRequest;
import by.grsu.iot.service.domain.request.user.RegistrationRequest;
import by.grsu.iot.service.domain.response.AuthenticationUser;
import by.grsu.iot.service.interf.crud.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserCrudService userCrudService;

    public AuthController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationUser> signIn(@RequestBody AuthenticationRequest data) {
        return ok(userCrudService.authenticate(data));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> singUp(@RequestBody RegistrationRequest data) {
        userCrudService.create(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
