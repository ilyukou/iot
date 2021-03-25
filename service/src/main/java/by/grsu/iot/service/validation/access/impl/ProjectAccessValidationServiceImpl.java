package by.grsu.iot.service.validation.access.impl;

import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.service.validation.access.interf.ProjectAccessValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectAccessValidationServiceImpl implements ProjectAccessValidationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProjectAccessValidationServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectAccessValidationServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationApplicationException("That user not has such project");
        }
    }

    private void throwExceptionIfWhoBeingRequestAndRequestedUsernameNotEquals(String whoBeingAskedUsername,
                                                                              String whoRequestedUsername) {
        if (!whoBeingAskedUsername.equals(whoRequestedUsername)) {
            throw new NotAccessForOperationApplicationException("That user not has such project");
        }
    }

    private void throwExceptionIfUserNotExist(String username) {
        if (!userRepository.isExistByUsername(username)) {
            throw new EntityNotFoundApplicationException("User with such username not exist");
        }
    }

    @Override
    public void checkPaginationInfoReadAccess(String whoBeingAskedUsername, String whoRequestedUsername) throws NotAccessForOperationApplicationException {
        throwExceptionIfWhoBeingRequestAndRequestedUsernameNotEquals(whoBeingAskedUsername, whoRequestedUsername);
        throwExceptionIfUserNotExist(whoRequestedUsername);
        throwExceptionIfUserNotExist(whoBeingAskedUsername);
    }

    @Override
    public void checkPageReadAccess(String whoBeingAskedUsername, String whoRequestedUsername) throws NotAccessForOperationApplicationException {
        throwExceptionIfWhoBeingRequestAndRequestedUsernameNotEquals(whoBeingAskedUsername, whoRequestedUsername);
        throwExceptionIfUserNotExist(whoRequestedUsername);
        throwExceptionIfUserNotExist(whoBeingAskedUsername);
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
