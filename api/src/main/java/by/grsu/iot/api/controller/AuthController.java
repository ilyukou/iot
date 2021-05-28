package by.grsu.iot.api.controller;

import by.grsu.iot.api.model.dto.email.SendEmailCodeRequest;
import by.grsu.iot.api.model.dto.user.*;
import by.grsu.iot.api.service.crud.user.EmailCrudService;
import by.grsu.iot.api.service.crud.user.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserCrudService userCrudService;
    private final EmailCrudService emailCrudService;

    public AuthController(
            UserCrudService userCrudService,
            EmailCrudService emailCrudService
    ) {
        this.userCrudService = userCrudService;
        this.emailCrudService = emailCrudService;
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

    @PostMapping("/activate")
    public ResponseEntity<Void> activateUser(@RequestBody ActivateUser data) {
        userCrudService.activateUser(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/restore")
    public ResponseEntity<Void> restorePassword(@RequestBody RestorePasswordForm data) {
        userCrudService.restorePassword(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/email/sendCode")
    public ResponseEntity<Void> sendCode(@RequestBody SendEmailCodeRequest data) {
        emailCrudService.sendEmailCode(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
