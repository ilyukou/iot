package by.grsu.iot.api.permission.resolver;

import by.grsu.iot.api.controller.crud.ProjectCrudController;
import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.repository.sql.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectCrudControllerPermissionResolver implements PermissionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCrudControllerPermissionResolver.class);

    private final ProjectRepository projectRepository;

    public ProjectCrudControllerPermissionResolver(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean support(String targetType) {
        return ProjectCrudController.class.getName().equals(targetType);
    }

    @Override
    public boolean hasPermissionCreate(String username, Long parentId) {
        return true;
    }

    @Override
    public boolean hasPermissionRead(String username, Long id) {
        return projectRepository.hasUserOwnerProject(username, id);
    }

    @Override
    public boolean hasPermissionReadPage(String username, Long parentId) {
        throw new IllegalArgumentException(Project.class.getName() + " hasn't parent");
    }

    @Override
    public boolean hasPermissionUpdate(String username, Long id) {
        return projectRepository.hasUserOwnerProject(username, id);
    }

    @Override
    public boolean hasPermissionDelete(String username, Long id) {
        return projectRepository.hasUserOwnerProject(username, id);
    }

}