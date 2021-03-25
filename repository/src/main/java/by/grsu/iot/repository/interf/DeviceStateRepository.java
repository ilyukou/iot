package by.grsu.iot.repository.interf;

import by.grsu.iot.model.state.GetStateRequest;
import by.grsu.iot.model.state.SetDeviceRequest;
import by.grsu.iot.model.state.StateRequest;

import java.util.Set;

/**
 * Repository for CRUD operation with {@link StateRequest}
 *
 * @author Ilyukou Ilya
 */
public interface DeviceStateRepository {

    // Wait GetStateRequest
    GetStateRequest getWaitDevice(String token);

    void removeWaitDevice(String token);

    void putWaitDevice(GetStateRequest device);

    boolean containsDevice(String token);


    // Wait SetDeviceRequest
    SetDeviceRequest getWaitRequest(String token);

    void removeWaitRequest(String token);

    void putWaitRequest(SetDeviceRequest request);

    boolean containsRequest(String token);


    // Processed GetStateRequest
    SetDeviceRequest getProcessedRequest(String token);

    void removeProcessedRequest(String token);

    boolean isExistProcessedRequest(String token);

    void putProcessedRequest(SetDeviceRequest request);


    // Processed SetDeviceRequest
    GetStateRequest getProcessedDevice(String token);

    void removeProcessedDevice(String token);

    boolean isExistProcessedDevice(String token);

    void putProcessedDevice(GetStateRequest device);

    Set<String> getWaitDeviceAndWaitRequestWithEqualsToken();
}
