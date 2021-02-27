package by.grsu.iot.service.validation.factory;

import by.grsu.iot.model.api.*;
import by.grsu.iot.service.domain.DeviceState;
import org.springframework.validation.DataBinder;

public interface DataBinderFactory {

    DataBinder createDataBinder(AuthenticationRequest authenticationRequest);

    DataBinder createDataBinder(RegistrationRequest registrationRequest);

    DataBinder createDataBinder(ProjectForm projectForm);

    DataBinder createDataBinder(DeviceForm deviceForm);

    DataBinder createDataBinder(DeviceFormUpdate deviceFormUpdate);

    DataBinder createDataBinder(DeviceState deviceState);
}
