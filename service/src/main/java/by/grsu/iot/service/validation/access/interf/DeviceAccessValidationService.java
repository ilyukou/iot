package by.grsu.iot.service.validation.access.interf;

import by.grsu.iot.service.exception.NotAccessForOperationException;

public interface DeviceAccessValidationService {

    void checkReadAccess(String username, Long deviceId) throws NotAccessForOperationException;

    void checkCreateAccess(String username, Long projectId) throws NotAccessForOperationException;

    void checkUpdateAccess(String username, Long deviceId) throws NotAccessForOperationException;

    void checkDeleteAccess(String username, Long deviceId) throws NotAccessForOperationException;
}
