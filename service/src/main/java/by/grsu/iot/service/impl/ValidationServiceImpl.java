package by.grsu.iot.service.impl;

import by.grsu.iot.service.domain.ValidationRule;
import by.grsu.iot.service.domain.ValidationStorage;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.interf.ValidationService;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final ValidationStorage validationStorage;

    public ValidationServiceImpl(ValidationStorage validationStorage) {
        this.validationStorage = validationStorage;
    }

    @Override
    public Map<String, ValidationRule> getRulesForEntity(String entity) {
        if(User.class.getSimpleName().equalsIgnoreCase(entity)){
            return validationStorage.getUser();
        }
        if(Project.class.getSimpleName().equalsIgnoreCase(entity)){
            return validationStorage.getProject();
        }
        if(Device.class.getSimpleName().equalsIgnoreCase(entity)){
            return validationStorage.getDevice();
        }

        throw new EntityNotFoundException("Not found validation for such entity={" + entity + "}");
    }
}