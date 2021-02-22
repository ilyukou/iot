package by.grsu.iot.service.validation.factory;

import by.grsu.iot.model.api.AuthenticationRequest;
import by.grsu.iot.model.api.DeviceForm;
import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.model.api.RegistrationRequest;
import by.grsu.iot.service.validation.validator.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;

@Service
public class DataBinderFactoryImpl implements DataBinderFactory{

    private final AuthenticationRequestValidator authenticationRequestValidator;
    private final RegistrationRequestValidator registrationRequestValidator;
    private final DeviceFormValidator deviceFormValidator;
    private final ProjectFormValidator projectFormValidator;

    public DataBinderFactoryImpl(
            AuthenticationRequestValidator authenticationRequestValidator,
            RegistrationRequestValidator registrationRequestValidator,
            DeviceFormValidator deviceFormValidator,
            ProjectFormValidator projectFormValidator) {
        this.authenticationRequestValidator = authenticationRequestValidator;
        this.registrationRequestValidator = registrationRequestValidator;
        this.deviceFormValidator = deviceFormValidator;
        this.projectFormValidator = projectFormValidator;
    }

    @Override
    public DataBinder createDataBinder(AuthenticationRequest authenticationRequest) {
        DataBinder dataBinder = new DataBinder(authenticationRequest);

        dataBinder.setValidator(authenticationRequestValidator);

        return dataBinder;
    }

    @Override
    public DataBinder createDataBinder(RegistrationRequest registrationRequest) {
        DataBinder dataBinder = new DataBinder(registrationRequest);

        dataBinder.setValidator(registrationRequestValidator);

        return dataBinder;
    }

    @Override
    public DataBinder createDataBinder(ProjectForm projectForm) {
        DataBinder dataBinder = new DataBinder(projectForm);

        dataBinder.setValidator(projectFormValidator);

        return dataBinder;
    }

    @Override
    public DataBinder createDataBinder(DeviceForm deviceForm) {
        DataBinder dataBinder = new DataBinder(deviceForm);

        dataBinder.setValidator(deviceFormValidator);

        return dataBinder;
    }
}
