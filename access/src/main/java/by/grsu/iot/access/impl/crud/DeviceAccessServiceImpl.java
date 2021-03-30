package by.grsu.iot.access.impl.crud;

import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.access.interf.crud.DeviceAccessService;
import org.springframework.stereotype.Service;

@Service
public class DeviceAccessServiceImpl implements DeviceAccessService {

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;

    public DeviceAccessServiceImpl(
            DeviceRepository deviceRepository,
            ProjectRepository projectRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationApplicationException("That user not has such project");
        }
    }

    private void throwExceptionIfUserNotOwnerDevice(String username, Long device) {
        if (!username.equals(deviceRepository.getDeviceOwnerUsername(device))) {
            throw new NotAccessForOperationApplicationException("That user not has such device");
        }
    }

    private void throwExceptionIfProjectNotExist(Long projectId) {
        if (!projectRepository.isExist(projectId)) {
            throw new EntityNotFoundApplicationException("Project with such id exist");
        }
    }

    @Override
    public void checkReadAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerDevice(username, deviceId);
    }

    @Override
    public void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkUpdateAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerDevice(username, deviceId);
    }

    @Override
    public void checkDeleteAccess(String username, Long deviceId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerDevice(username, deviceId);
    }
}
