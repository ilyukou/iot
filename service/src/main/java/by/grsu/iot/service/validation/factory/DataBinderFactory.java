package by.grsu.iot.service.validation.factory;

import by.grsu.iot.model.api.AuthenticationRequest;
import by.grsu.iot.model.api.DeviceForm;
import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.model.api.RegistrationRequest;
import org.springframework.validation.DataBinder;

public interface DataBinderFactory {

    DataBinder createDataBinder(AuthenticationRequest authenticationRequest);

    DataBinder createDataBinder(RegistrationRequest registrationRequest);

    DataBinder createDataBinder(ProjectForm projectForm);

    DataBinder createDataBinder(DeviceForm deviceForm);
}
