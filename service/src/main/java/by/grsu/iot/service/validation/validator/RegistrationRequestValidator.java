package by.grsu.iot.service.validation.validator;

import by.grsu.iot.service.domain.RegistrationRequest;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.UserValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class RegistrationRequestValidator implements ApplicationValidator {

    private final UserValidationService userValidationService;

    public RegistrationRequestValidator(UserValidationService userValidationService) {
        this.userValidationService = userValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationRequest obj = (RegistrationRequest) target;

        try {
            userValidationService.validatePassword(obj.getPassword());
            userValidationService.validateUsername(obj.getUsername());
            userValidationService.validateEmail(obj.getEmail());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
