package by.grsu.iot.api.repository.sql.project.thing;

import by.grsu.iot.api.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.api.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.api.model.exception.ConflictException;
import by.grsu.iot.api.model.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DeviceStateRepositoryImpl implements DeviceStateRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeviceStateRepositoryImpl.class);

    private final DeviceRepository deviceRepository;

    private final Map<String, GetStateRequest> devices = new ConcurrentHashMap<>();
    private final Map<String, SetDeviceRequest> requests = new ConcurrentHashMap<>();

    private final Map<String, SetDeviceRequest> requestsProcessed = new ConcurrentHashMap<>();
    private final Map<String, GetStateRequest> devicesProcessed = new ConcurrentHashMap<>();


    public DeviceStateRepositoryImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public synchronized GetStateRequest getWaitGetStateRequest(String token) {
        return devices.get(token);
    }

    @Override
    public synchronized void removeWaitGetStateRequest(String token) {
        devices.remove(token);
    }

    @Override
    public synchronized void putWaitGetStateRequest(GetStateRequest device) {
        if (devices.containsKey(device.getToken())) {
            throw new ConflictException("Request with such token exist");
        }

        devices.put(device.getToken(), device);
        LOGGER.info("[GET] New device " + device);
        LOGGER.info("requests=" + requests.size() + "; devices=" + devices.size());
        notifyAll();
    }

    @Override
    public synchronized boolean isExistWaitGetStateRequest(String token) {
        return devices.containsKey(token);
    }

    @Override
    public synchronized SetDeviceRequest getWaitSetDeviceRequest(String token) {
        return requests.get(token);
    }

    @Override
    public synchronized void removeWaitSetDeviceRequest(String token) {
        requests.remove(token);
    }

    @Override
    public synchronized void putWaitSetDeviceRequest(SetDeviceRequest request) {
        if (requests.containsKey(request.getToken())) {
            throw new ConflictException("Request with such token exist");
        }

        requests.put(request.getToken(), request);
        LOGGER.info("[SET] New request " + request);
        LOGGER.info("requests=" + requests.size() + "; devices=" + devices.size());
        notifyAll();
    }

    @Override
    public synchronized boolean isExistWaitSetDeviceRequest(String token) {
        return requests.containsKey(token);
    }

    @Override
    public synchronized SetDeviceRequest getProcessedSetDeviceRequest(String token) {
        while (!isExistProcessedSetDeviceRequest(token)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SetDeviceRequest request = requestsProcessed.get(token);
        requestsProcessed.remove(token);
        requests.remove(token);

        deviceRepository.changeState(request.getState(), request.getToken());
        notifyAll();
        return request;
    }

    @Override
    public synchronized void removeProcessedSetDeviceRequest(String token) {
        requestsProcessed.remove(token);
    }

    @Override
    public synchronized boolean isExistProcessedSetDeviceRequest(String token) {
        return requestsProcessed.containsKey(token);
    }

    @Override
    public synchronized void putSetDeviceRequest(SetDeviceRequest request) {
        requestsProcessed.put(request.getToken(), request);
        notifyAll();
    }

    @Override
    public synchronized GetStateRequest getProcessedGetStateRequest(String token) {
        while (!isExistProcessedGetStateRequest(token)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetStateRequest device = devicesProcessed.get(token);
        devicesProcessed.remove(token);
        devices.remove(token);

        notifyAll();
        return device;
    }

    @Override
    public synchronized void removeProcessedGetStateRequest(String token) {
        devicesProcessed.remove(token);
    }

    @Override
    public synchronized boolean isExistProcessedGetStateRequest(String token) {
        return devicesProcessed.containsKey(token);
    }

    @Override
    public synchronized void putProcessedGetStateRequest(GetStateRequest device) {
        devicesProcessed.put(device.getToken(), device);
        notifyAll();
    }

    @Override
    public synchronized Set<String> getWaitGetStateRequestAndWaitSetDeviceRequestWithEqualsToken() {
        if (devices.size() == 0 || requests.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<String> keys = CollectionUtil.getSharedSetKeys(requests.keySet(), devices.keySet());

        if (keys.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return keys;
    }
}
