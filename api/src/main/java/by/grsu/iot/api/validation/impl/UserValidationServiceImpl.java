package by.grsu.iot.api.validation.impl;

import by.grsu.iot.api.dto.validation.ValidationRule;
import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.util.StringUtil;
import by.grsu.iot.api.validation.ValidationStorage;
import by.grsu.iot.api.validation.interf.UserValidationService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class UserValidationServiceImpl implements UserValidationService {

    private final ValidationRule passwordRule;
    private final ValidationRule usernameRule;

    public UserValidationServiceImpl(ValidationStorage validationStorage) {
        this.passwordRule = validationStorage.getUser().get("password");
        this.usernameRule = validationStorage.getUser().get("username");
    }

    @Override
    public void validatePassword(String password) throws BadRequestException {
        if(password == null){
            throw new BadRequestException("password", "Password is null");
        }

        if(password.length() < passwordRule.getMin()){
            throw new BadRequestException("password", "Password size is less than " + passwordRule.getMin());
        }

        if(password.length() > passwordRule.getMax()){
            throw new BadRequestException("password", "Password size is more than " + passwordRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(passwordRule.getSpace(), password)){
            throw new BadRequestException("password", "Password string has space");
        }
    }

    @Override
    public void validateUsername(String username) throws BadRequestException {
        if(username == null){
            throw new BadRequestException("username", "Username is null");
        }

        if(username.length() < usernameRule.getMin()){
            throw new BadRequestException("username", "Username size is less than " + usernameRule.getMin());
        }

        if(username.length() > usernameRule.getMax()){
            throw new BadRequestException("username", "Username size is more than " + usernameRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(usernameRule.getSpace(), username)){
            throw new BadRequestException("username", "Username string has space");
        }
    }

    @Override
    public void validateEmail(String email) throws BadRequestException {
        if(email == null){
            throw new BadRequestException("email", "Email is null");
        }

        if(!EmailValidator.getInstance().isValid(email)){
            throw new BadRequestException("email", "Incorrect email");
        };
    }
}
