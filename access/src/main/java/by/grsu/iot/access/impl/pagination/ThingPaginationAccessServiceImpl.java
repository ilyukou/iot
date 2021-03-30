package by.grsu.iot.access.impl.pagination;

import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.access.interf.pagination.ThingPaginationAccessService;
import org.springframework.stereotype.Service;

@Service
public class ThingPaginationAccessServiceImpl implements ThingPaginationAccessService {

    private final ProjectRepository projectRepository;

    public ThingPaginationAccessServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void checkPaginationInfoReadAccess(Long projectId, String whoRequestedUsername) throws NotAccessForOperationApplicationException {
        throwExceptionIfProjectNotExist(projectId);
        throwExceptionIfUserNotOwnerProject(whoRequestedUsername, projectId);
    }

    @Override
    public void checkPageReadAccess(Long projectId, String whoRequestedUsername) throws NotAccessForOperationApplicationException {
        throwExceptionIfProjectNotExist(projectId);
        throwExceptionIfUserNotOwnerProject(whoRequestedUsername, projectId);
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationApplicationException("That user not has such project");
        }
    }

    private void throwExceptionIfProjectNotExist(Long projectId) {
        if (!projectRepository.isExist(projectId)) {
            throw new EntityNotFoundApplicationException("Project with such id exist");
        }
    }
}
