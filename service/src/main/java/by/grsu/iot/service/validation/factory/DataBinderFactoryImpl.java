package by.grsu.iot.service.validation.factory;

import by.grsu.iot.model.api.*;
import by.grsu.iot.service.domain.DeviceState;
import by.grsu.iot.service.validation.validator.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;

@Service
public class DataBinderFactoryImpl implements DataBinderFactory{

    private final AuthenticationRequestValidator authenticationRequestValidator;
    private final RegistrationRequestValidator registrationRequestValidator;
    private final DeviceFormValidator deviceFormValidator;
    private final DeviceFormUpdateValidator deviceFormUpdateValidator;
    private final ProjectFormValidator projectFormValidator;
    private final DeviceStateValidator deviceStateValidator;

    public DataBinderFactoryImpl(
            AuthenticationRequestValidator authenticationRequestValidator,
            RegistrationRequestValidator registrationRequestValidator,
            DeviceFormValidator deviceFormValidator,
            DeviceFormUpdateValidator deviceFormUpdateValidator, ProjectFormValidator projectFormValidator,
            DeviceStateValidator deviceStateValidator) {
        this.authenticationRequestValidator = authenticationRequestValidator;
        this.registrationRequestValidator = registrationRequestValidator;
        this.deviceFormValidator = deviceFormValidator;
        this.deviceFormUpdateValidator = deviceFormUpdateValidator;
        this.projectFormValidator = projectFormValidator;
        this.deviceStateValidator = deviceStateValidator;
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

    @Override
    public DataBinder createDataBinder(DeviceFormUpdate deviceFormUpdate) {
        DataBinder dataBinder = new DataBinder(deviceFormUpdate);

        dataBinder.setValidator(deviceFormUpdateValidator);

        return dataBinder;
    }

    @Override
    public DataBinder createDataBinder(DeviceState deviceState) {
        DataBinder dataBinder = new DataBinder(deviceState);

        dataBinder.setValidator(deviceStateValidator);

        return dataBinder;
    }
}
