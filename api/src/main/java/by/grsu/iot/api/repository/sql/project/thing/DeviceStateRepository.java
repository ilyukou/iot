package by.grsu.iot.api.repository.sql.project.thing;

import by.grsu.iot.api.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.api.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.api.model.dto.thing.device.state.StateRequest;

import java.util.Set;

/**
 * Repository for CRUD operation with {@link StateRequest}
 *
 * @author Ilyukou Ilya
 */
public interface DeviceStateRepository {

    // Wait GetStateRequest
    GetStateRequest getWaitGetStateRequest(String token);

    void removeWaitGetStateRequest(String token);

    void putWaitGetStateRequest(GetStateRequest device);

    boolean isExistWaitGetStateRequest(String token);


    // Wait SetDeviceRequest
    SetDeviceRequest getWaitSetDeviceRequest(String token);

    void removeWaitSetDeviceRequest(String token);

    void putWaitSetDeviceRequest(SetDeviceRequest request);

    boolean isExistWaitSetDeviceRequest(String token);


    // Processed SetDeviceRequest
    SetDeviceRequest getProcessedSetDeviceRequest(String token);

    void removeProcessedSetDeviceRequest(String token);

    boolean isExistProcessedSetDeviceRequest(String token);

    void putSetDeviceRequest(SetDeviceRequest request);


    // Processed GetStateRequest
    GetStateRequest getProcessedGetStateRequest(String token);

    void removeProcessedGetStateRequest(String token);

    boolean isExistProcessedGetStateRequest(String token);

    void putProcessedGetStateRequest(GetStateRequest device);

    Set<String> getWaitGetStateRequestAndWaitSetDeviceRequestWithEqualsToken();
}
