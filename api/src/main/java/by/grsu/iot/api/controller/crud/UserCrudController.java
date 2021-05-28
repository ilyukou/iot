package by.grsu.iot.api.controller.crud;

import by.grsu.iot.api.model.dto.user.UserUpdateForm;
import by.grsu.iot.api.service.crud.user.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/crud/user")
public class UserCrudController {

    private final UserCrudService userCrudService;

    public UserCrudController(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PutMapping
    public ResponseEntity<Void> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateForm userUpdateForm
    ) {
        userCrudService.update(userDetails.getUsername(), userUpdateForm);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
