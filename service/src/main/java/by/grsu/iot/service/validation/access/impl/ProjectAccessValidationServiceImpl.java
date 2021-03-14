package by.grsu.iot.service.validation.access.impl;

import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.validation.access.interf.ProjectAccessValidationService;
import org.springframework.stereotype.Service;

@Service
public class ProjectAccessValidationServiceImpl implements ProjectAccessValidationService {

    private final ProjectRepository projectRepository;

    public ProjectAccessValidationServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationException("That user not has such project");
        }
    }

    @Override
    public void checkReadAccess(String username, Long projectId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkCreateAccess(String username) throws NotAccessForOperationException {
        // ignore
    }

    @Override
    public void checkUpdateAccess(String username, Long projectId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkDeleteAccess(String username, Long projectId) throws NotAccessForOperationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }
}
