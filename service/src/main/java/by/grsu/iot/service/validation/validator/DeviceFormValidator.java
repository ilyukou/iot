package by.grsu.iot.service.validation.validator;

import by.grsu.iot.model.api.DeviceForm;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.DeviceValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class DeviceFormValidator implements Validator {

    private final DeviceValidationService deviceValidationService;

    public DeviceFormValidator(DeviceValidationService deviceValidationService) {
        this.deviceValidationService = deviceValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DeviceForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DeviceForm obj = (DeviceForm) target;

        try {
            deviceValidationService.validateName(obj.getName());
            deviceValidationService.validateState(obj.getState());
            deviceValidationService.validateStates(obj.getStates());

            if(obj.getStates().size() < 2){
                throw new BadRequestException("states", "States size is less than 2");
            }

            if(!obj.getStates().contains(obj.getState())){
                throw new BadRequestException("state", "State not contains in states");
            }

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
