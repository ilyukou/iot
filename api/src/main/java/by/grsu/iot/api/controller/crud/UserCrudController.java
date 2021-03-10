package by.grsu.iot.api.controller.crud;

import by.grsu.iot.service.domain.AuthenticationRequest;
import by.grsu.iot.service.domain.AuthenticationUser;
import by.grsu.iot.service.domain.RegistrationRequest;
import by.grsu.iot.service.interf.crud.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/crud/user")
public class UserCrudController {

    private final UserCrudService userCrudService;

    public UserCrudController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @GetMapping
    public ResponseEntity<AuthenticationUser> signIn(@RequestBody AuthenticationRequest data) {
        return ok(userCrudService.authenticate(data));
    }

    @PostMapping
    public ResponseEntity<Void> singUp(@RequestBody RegistrationRequest data) {
        userCrudService.create(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
