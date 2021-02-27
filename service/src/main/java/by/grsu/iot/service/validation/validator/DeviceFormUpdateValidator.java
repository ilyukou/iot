package by.grsu.iot.service.validation.validator;

import by.grsu.iot.model.api.DeviceForm;
import by.grsu.iot.model.api.DeviceFormUpdate;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.validation.interf.DeviceValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class DeviceFormUpdateValidator implements ApplicationValidator {

    private final DeviceValidationService deviceValidationService;

    public DeviceFormUpdateValidator(DeviceValidationService deviceValidationService) {
        this.deviceValidationService = deviceValidationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DeviceFormUpdate.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            if (target == null){
                throw new BadRequestException("Device is null");
            }

            DeviceFormUpdate obj = (DeviceFormUpdate) target;

            if (obj.getName() == null){
                throw new BadRequestException("name", "Name is null");
            }


            deviceValidationService.validateName(obj.getName());

        } catch (BadRequestException e){
            Object[] arr = {e};
            errors.rejectValue(e.getField(), "400", arr, e.getMessage());
        }
    }
}
