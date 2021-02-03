package by.grsu.iot.api.validator;

import by.grsu.iot.api.dto.RegistrationRequest;
import by.grsu.iot.api.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class RegistrationRequestValidator implements ValidatorService {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            validateParam(target);
        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }

    @Override
    public void validateParam(Object target) throws BadRequestException {
        RegistrationRequest request = (RegistrationRequest) target;
        // EMAIL
        if(request.getEmail() == null){
            throw new BadRequestException("email", "Email not set");
        }

        // USERNAME
        if(request.getUsername() == null || request.getUsername().length() < 5){
            throw new BadRequestException("username", "Username not set or length less than 5");
        }

        // PASSWORD
        if(request.getPassword() == null || request.getPassword().length() < 8){
            throw new BadRequestException("password", "Password not set or length less than 8");
        }
    }
}
