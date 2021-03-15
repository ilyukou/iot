package by.grsu.iot.service.validation.access.impl;

import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.validation.access.interf.DeviceAccessValidationService;
import org.springframework.stereotype.Service;

@Service
public class DeviceAccessValidationServiceImpl implements DeviceAccessValidationService {

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;

    public DeviceAccessValidationServiceImpl(
            DeviceRepository deviceRepository,
            ProjectRepository projectRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
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

    @Override
    public void checkPaginationInfoReadAccess(Long project, String whoRequestedUsername) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerProject(whoRequestedUsername, project);
    }

    @Override
    public void checkPageReadAccess(Long projectId, String whoRequestedUsername) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerProject(whoRequestedUsername, projectId);
    }

    @Override
    public void checkReadAccess(String username, Long deviceId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerDevice(username, deviceId);
    }

    @Override
    public void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkUpdateAccess(String username, Long deviceId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerDevice(username, deviceId);
    }

    @Override
    public void checkDeleteAccess(String username, Long deviceId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerDevice(username, deviceId);
    }
}
