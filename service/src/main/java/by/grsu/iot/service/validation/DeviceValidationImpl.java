package by.grsu.iot.service.validation;

import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.domain.form.Form;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.validation.util.DataBinderValidation;
import org.springframework.stereotype.Service;

@Service
public class DeviceValidationImpl implements DeviceValidation {

    private final ProjectRepository projectRepository;
    private final DeviceRepository deviceRepository;

    public DeviceValidationImpl(
            ProjectRepository projectRepository,
            DeviceRepository deviceRepository
    ) {
        this.projectRepository = projectRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public void validateCreateDevice(String username, Long project, Form form) {
        DataBinderValidation.validateParam(form);
        throwExceptionIfUserNotOwnerProject(username, project);
    }

    @Override
    public void validateReadDevice(String username, Long device) {
        throwExceptionIfUserNotOwnerDevice(username, device);
    }

    @Override
    public void validateUpdateDevice(String username, Long device, Form form) {
        DataBinderValidation.validateParam(form);
        throwExceptionIfUserNotOwnerDevice(username, device);
    }

    @Override
    public void validateDeleteDevice(String username, Long device) {
        throwExceptionIfUserNotOwnerDevice(username, device);
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationException("That user not has such project");
        }
    }

    private void throwExceptionIfUserNotOwnerDevice(String username, Long device) {
        if (!username.equals(deviceRepository.getDeviceOwnerUsername(device))) {
            throw new NotAccessForOperationException("That user not has such device");
        }
    }
}
