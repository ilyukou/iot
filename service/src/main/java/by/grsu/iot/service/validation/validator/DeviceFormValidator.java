package by.grsu.iot.service.validation.validator;

import by.grsu.iot.service.domain.form.DeviceForm;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.DeviceValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class DeviceFormValidator implements ApplicationValidator {

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
        try {
            if (target == null){
                throw new BadRequestException("Device is null");
            }

            DeviceForm obj = (DeviceForm) target;

            if (obj.getName() == null){
                throw new BadRequestException("name", "Name is null");
            }

            if (obj.getState() == null){
                throw new BadRequestException("state", "State is null");
            }

            if (obj.getStates() == null){
                throw new BadRequestException("states", "States is null");
            }

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
