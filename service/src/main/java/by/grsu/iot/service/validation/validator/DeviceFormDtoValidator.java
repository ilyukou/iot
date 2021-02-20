package by.grsu.iot.service.validation.validator;

import by.grsu.iot.model.api.DeviceFormDto;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.DeviceValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class DeviceFormDtoValidator implements Validator {

    private final DeviceValidationService deviceValidationService;

    public DeviceFormDtoValidator(DeviceValidationService deviceValidationService) {
        this.deviceValidationService = deviceValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DeviceFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DeviceFormDto obj = (DeviceFormDto) target;

        try {
            deviceValidationService.validateName(obj.getName());
            deviceValidationService.validateState(obj.getState());
            deviceValidationService.validateStates(obj.getStates());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
