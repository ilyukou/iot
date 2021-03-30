package by.grsu.iot.access.impl.pagination;

import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.access.interf.pagination.ProjectPaginationAccessService;
import org.springframework.stereotype.Service;

@Service
public class ProjectPaginationAccessServiceImpl implements ProjectPaginationAccessService {

    private final UserRepository userRepository;

    public ProjectPaginationAccessServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
