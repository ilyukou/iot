package by.grsu.iot.api.validation.impl;

import by.grsu.iot.api.dto.validation.ValidationRule;
import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.util.StringUtil;
import by.grsu.iot.api.validation.ValidationStorage;
import by.grsu.iot.api.validation.interf.DeviceValidationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceValidationServiceImpl implements DeviceValidationService {

    private final ValidationRule nameRule;
    private final ValidationRule stateRule;

    public DeviceValidationServiceImpl(ValidationStorage validationStorage) {
        this.nameRule = validationStorage.getDevice().get("name");
        this.stateRule = validationStorage.getDevice().get("state");
    }

    @Override
    public void validateName(String name) throws BadRequestException {
        if(name == null){
            throw new BadRequestException("name", "Name is null");
        }

        if(name.length() < nameRule.getMin()){
            throw new BadRequestException("name", "Name size is less than " + nameRule.getMin());
        }

        if(name.length() > nameRule.getMax()){
            throw new BadRequestException("name", "Name size is more than " + nameRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(nameRule.getSpace(), name)){
            throw new BadRequestException("name", "Name string has space");
        }
    }

    @Override
    public void validateStates(List<String> states) throws BadRequestException {
        states.forEach(this::validateState);
    }

    @Override
    public void validateState(String state) throws BadRequestException {
        if(state == null){
            throw new BadRequestException("state", "State is null");
        }

        if(state.length() < nameRule.getMin()){
            throw new BadRequestException("state", "State size is less than " + nameRule.getMin());
        }

        if(state.length() > nameRule.getMax()){
            throw new BadRequestException("state", "State size is more than " + nameRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(stateRule.getSpace(), state)){
            throw new BadRequestException("state", "State string has space");
        }
    }
}
