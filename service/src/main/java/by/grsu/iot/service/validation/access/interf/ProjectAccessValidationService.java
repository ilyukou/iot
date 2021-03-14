package by.grsu.iot.service.validation.access.interf;

import by.grsu.iot.service.exception.NotAccessForOperationException;

public interface ProjectAccessValidationService {

    void checkReadAccess(String username, Long projectId) throws NotAccessForOperationException;

    void checkCreateAccess(String username) throws NotAccessForOperationException;

    void checkUpdateAccess(String username, Long projectId) throws NotAccessForOperationException;

    void checkDeleteAccess(String username, Long projectId) throws NotAccessForOperationException;
}
