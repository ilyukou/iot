package by.grsu.iot.access.impl.crud;

import by.grsu.iot.model.exception.NotAccessForOperationApplicationException;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.access.interf.crud.SensorAccessService;
import org.springframework.stereotype.Service;

@Service
public class SensorAccessServiceImpl implements SensorAccessService {

    private final SensorRepository sensorRepository;
    private final ProjectRepository projectRepository;

    public SensorAccessServiceImpl(
            SensorRepository sensorRepository,
            ProjectRepository projectRepository
    ) {
        this.sensorRepository = sensorRepository;
        this.projectRepository = projectRepository;
    }

    private void throwExceptionIfUserNotOwnerProject(String username, Long project) {
        if (!username.equals(projectRepository.getProjectOwnerUsername(project))) {
            throw new NotAccessForOperationApplicationException("That user not has such project");
        }
    }

    private void throwExceptionIfUserNotOwnerSensor(String username, Long sensorId) {
        if (!username.equals(sensorRepository.getOwnerUsername(sensorId))) {
            throw new NotAccessForOperationApplicationException("That user not has such device");
        }
    }

    @Override
    public void checkReadAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerSensor(username, sensorId);
    }

    @Override
    public void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerProject(username, projectId);
    }

    @Override
    public void checkUpdateAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerSensor(username, sensorId);
    }

    @Override
    public void checkDeleteAccess(String username, Long sensorId) throws NotAccessForOperationApplicationException {
        throwExceptionIfUserNotOwnerSensor(username, sensorId);
    }
}
