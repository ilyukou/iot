package by.grsu.iot.api.permission.resolver;

import by.grsu.iot.api.controller.crud.SensorCrudController;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.SensorRepository;
import org.springframework.stereotype.Service;

@Service
public class SensorCrudControllerPermissionResolver implements PermissionResolver {

    private final SensorRepository sensorRepository;
    private final ProjectRepository projectRepository;

    public SensorCrudControllerPermissionResolver(SensorRepository sensorRepository, ProjectRepository projectRepository) {
        this.sensorRepository = sensorRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean support(String targetType) {
        return SensorCrudController.class.getName().equals(targetType);
    }

    @Override
    public boolean hasPermissionCreate(String username, Long parentId) {
        return projectRepository.hasUserOwnerProject(username, parentId);
    }

    @Override
    public boolean hasPermissionRead(String username, Long id) {
        return sensorRepository.hasUserOwnerSensor(username, id);
    }

    @Override
    public boolean hasPermissionReadPage(String username, Long parentId) {
        return projectRepository.hasUserOwnerProject(username, parentId);
    }

    @Override
    public boolean hasPermissionUpdate(String username, Long id) {
        return sensorRepository.hasUserOwnerSensor(username, id);
    }

    @Override
    public boolean hasPermissionDelete(String username, Long id) {
        return sensorRepository.hasUserOwnerSensor(username, id);
    }
}
