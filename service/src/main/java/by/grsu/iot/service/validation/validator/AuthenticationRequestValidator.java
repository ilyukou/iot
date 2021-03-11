package by.grsu.iot.service.validation.validator;

import by.grsu.iot.service.domain.AuthenticationRequest;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.UserValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class AuthenticationRequestValidator implements ApplicationValidator {

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
