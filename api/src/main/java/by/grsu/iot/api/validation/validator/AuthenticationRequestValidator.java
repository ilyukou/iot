package by.grsu.iot.api.validation.validator;

import by.grsu.iot.api.dto.AuthenticationRequest;
import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.validation.interf.UserValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AuthenticationRequestValidator implements Validator {

    private final UserValidationService userValidationService;

    public AuthenticationRequestValidator(UserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthenticationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthenticationRequest obj = (AuthenticationRequest) target;

        try {
            userValidationService.validatePassword(obj.getPassword());
            userValidationService.validateUsername(obj.getUsername());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
