package by.grsu.iot.api.permission.resolver;

import by.grsu.iot.api.controller.crud.DeviceCrudController;
import by.grsu.iot.api.repository.sql.DeviceRepository;
import by.grsu.iot.api.repository.sql.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceCrudControllerPermissionResolver implements PermissionResolver {

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;

    public DeviceCrudControllerPermissionResolver(DeviceRepository deviceRepository, ProjectRepository projectRepository) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean support(String targetType) {
        return DeviceCrudController.class.getName().equals(targetType);
    }

    @Override
    public boolean hasPermissionCreate(String username, Long parentId) {
        return projectRepository.hasUserOwnerProject(username, parentId);
    }

    @Override
    public boolean hasPermissionRead(String username, Long id) {
        return deviceRepository.hasUserOwnerDevice(username, id);
    }

    @Override
    public boolean hasPermissionReadPage(String username, Long parentId) {
        return projectRepository.hasUserOwnerProject(username, parentId);
    }

    @Override
    public boolean hasPermissionUpdate(String username, Long id) {
        return deviceRepository.hasUserOwnerDevice(username, id);
    }

    @Override
    public boolean hasPermissionDelete(String username, Long id) {
        return deviceRepository.hasUserOwnerDevice(username, id);
    }
}
