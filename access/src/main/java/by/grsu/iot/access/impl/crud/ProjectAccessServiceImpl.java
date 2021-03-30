package by.grsu.iot.access.impl.crud;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.access.interf.crud.ProjectAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectAccessServiceImpl implements ProjectAccessService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProjectAccessServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectAccessServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationApplicationException("That user not has such project");
        }
    }

    @Override
    public void checkReadAccess(String username, Long projectId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkCreateAccess(String username) throws NotAccessForOperationApplicationException {
        // ignore
    }

    @Override
    public void checkUpdateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkDeleteAccess(String username, Long projectId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }
}
