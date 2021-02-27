package by.grsu.iot.service.validation.impl;

import by.grsu.iot.service.domain.ValidationRule;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.util.CollectionUtil;
import by.grsu.iot.service.util.StringUtil;
import by.grsu.iot.service.domain.ValidationStorage;
import by.grsu.iot.service.validation.interf.DeviceValidationService;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@PropertySource("classpath:application-const.properties")
@Service
public class DeviceValidationServiceImpl implements DeviceValidationService {

    @Value("${by.grsu.iot.service.device.states.size.min}")
    private Integer STATES_MIN_SIZE;

    @Value("${by.grsu.iot.service.device.states.size.max}")
    private Integer STATES_MAX_SIZE;

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
            throw new BadRequestException("name", "Name length is less than " + nameRule.getMin());
        }

        if(name.length() > nameRule.getMax()){
            throw new BadRequestException("name", "Name length is more than " + nameRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(nameRule.getSpace(), name)){
            throw new BadRequestException("name", "Name string has space");
        }
    }

    @Override
    public void validateStates(List<String> states) throws BadRequestException {
        if(states.size() < STATES_MIN_SIZE){
            throw new BadRequestException("states", "States size is less than " + STATES_MIN_SIZE);
        }

        if(states.size() > STATES_MAX_SIZE){
            throw new BadRequestException("states", "States size is more than " + STATES_MAX_SIZE);
        }

        for (int i = 0; i < states.size(); i++) {
            if(states.get(i) == null){
                throw new BadRequestException("states", "State[" + i + "] is null");
            }

            if(states.get(i).length() < nameRule.getMin()){
                throw new BadRequestException("states", "State[" + i + "] length is less than " + nameRule.getMin());
            }

            if(states.get(i).length() > nameRule.getMax()){
                throw new BadRequestException("states", "State[" + i + "] length is more than " + nameRule.getMax());
            }

            if(!StringUtil.isStringValidByParam(stateRule.getSpace(), states.get(i))){
                throw new BadRequestException("states", "State[" + i + "] string has space");
            }
        }

        if (CollectionUtil.hasListDuplicates(states)){
            throw new BadRequestException("states", "Found duplicates string in states");
        }
    }

    @Override
    public void validateState(String state) throws BadRequestException {
        if(state == null){
            throw new BadRequestException("state", "State is null");
        }

        if(state.length() < nameRule.getMin()){
            throw new BadRequestException("state", "State length is less than " + nameRule.getMin());
        }

        if(state.length() > nameRule.getMax()){
            throw new BadRequestException("state", "State length is more than " + nameRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(stateRule.getSpace(), state)){
            throw new BadRequestException("state", "State string has space");
        }
    }
}
