package by.grsu.iot.service.validation;

import by.grsu.iot.service.domain.form.Form;

public interface DeviceValidation {
    void validateCreateDevice(String username, Long project, Form form);

    void validateReadDevice(String username, Long device);

    void validateUpdateDevice(String username, Long device, Form form);

    void validateDeleteDevice(String username, Long device);
}
