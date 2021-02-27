package by.grsu.iot.service.validation.validator;

import by.grsu.iot.service.domain.DeviceState;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.DeviceValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class DeviceStateValidator implements Validator{

    private final DeviceValidationService deviceValidationService;

    public DeviceStateValidator(DeviceValidationService deviceValidationService) {
        this.deviceValidationService = deviceValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DeviceState.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            if (target == null){
                throw new BadRequestException("Device is null");
            }

            DeviceState obj = (DeviceState) target;

            if (obj.getState() == null){
                throw new BadRequestException("state", "State is null");
            }

            deviceValidationService.validateState(obj.getState());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
