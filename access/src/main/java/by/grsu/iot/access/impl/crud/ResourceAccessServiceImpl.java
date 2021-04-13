package by.grsu.iot.access.impl.crud;

import by.grsu.iot.access.interf.crud.ProjectAccessService;
import by.grsu.iot.access.interf.crud.ResourceAccessService;
import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.repository.interf.ResourceRepository;
import org.springframework.stereotype.Service;

@Service
public class ResourceAccessServiceImpl implements ResourceAccessService {

    private final ResourceRepository resourceRepository;
    private final ProjectAccessService projectAccessService;

    public ResourceAccessServiceImpl(
            ResourceRepository resourceRepository,
            ProjectAccessService projectAccessService
    ) {
        this.resourceRepository = resourceRepository;
        this.projectAccessService = projectAccessService;
    }

    @Override
    public void checkReadAccess(String username, String filename) throws NotAccessForOperationApplicationException {
        if (!resourceRepository.isExist(filename) || !resourceRepository.getOwnerUsername(filename).equals(username)){
            throw new NotAccessForOperationApplicationException("That user not has such resource");
        }
    }

    @Override
    public void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException {
        projectAccessService.checkUpdateAccess(username, projectId);
    }
}
