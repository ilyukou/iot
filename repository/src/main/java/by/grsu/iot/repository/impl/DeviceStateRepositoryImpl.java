package by.grsu.iot.repository.impl;

import by.grsu.iot.model.dto.state.GetStateRequest;
import by.grsu.iot.model.dto.state.SetDeviceRequest;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.model.exception.ConflictException;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.DeviceStateRepository;
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

    private Map<String, GetStateRequest> devices = new ConcurrentHashMap<>();
    private Map<String, SetDeviceRequest> requests = new ConcurrentHashMap<>();

    private Map<String, SetDeviceRequest> requestsProcessed = new ConcurrentHashMap<>();
    private Map<String, GetStateRequest> devicesProcessed = new ConcurrentHashMap<>();


    public DeviceStateRepositoryImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public synchronized GetStateRequest getWaitDevice(String token) {
        return devices.get(token);
    }

    @Override
    public synchronized void removeWaitDevice(String token) {
        devices.remove(token);
    }

    @Override
    public synchronized void putWaitDevice(GetStateRequest device) {
        if (devices.containsKey(device.getToken())) {
            throw new ConflictException("Request with such token exist");
        }

        devices.put(device.getToken(), device);
        LOGGER.info("[GET] New device " + device);
        LOGGER.info("requests=" + requests.size() + "; devices=" + devices.size());
        notifyAll();
    }

    @Override
    public synchronized boolean containsDevice(String token) {
        return devices.containsKey(token);
    }

    @Override
    public synchronized SetDeviceRequest getWaitRequest(String token) {
        return requests.get(token);
    }

    @Override
    public synchronized void removeWaitRequest(String token) {
        requests.remove(token);
    }

    @Override
    public synchronized void putWaitRequest(SetDeviceRequest request) {
        if (requests.containsKey(request.getToken())) {
            throw new ConflictException("Request with such token exist");
        }

        requests.put(request.getToken(), request);
        LOGGER.info("[SET] New request " + request);
        LOGGER.info("requests=" + requests.size() + "; devices=" + devices.size());
        notifyAll();
    }

    @Override
    public synchronized boolean containsRequest(String token) {
        return requests.containsKey(token);
    }

    @Override
    public synchronized SetDeviceRequest getProcessedRequest(String token) {
        while (!isExistProcessedRequest(token)) {
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
    public synchronized void removeProcessedRequest(String token) {
        requestsProcessed.remove(token);
    }

    @Override
    public synchronized boolean isExistProcessedRequest(String token) {
        return requestsProcessed.containsKey(token);
    }

    @Override
    public synchronized void putProcessedRequest(SetDeviceRequest request) {
        requestsProcessed.put(request.getToken(), request);
        notifyAll();
    }

    @Override
    public synchronized GetStateRequest getProcessedDevice(String token) {
        while (!isExistProcessedDevice(token)) {
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
    public synchronized void removeProcessedDevice(String token) {
        devicesProcessed.remove(token);
    }

    @Override
    public synchronized boolean isExistProcessedDevice(String token) {
        return devicesProcessed.containsKey(token);
    }

    @Override
    public synchronized void putProcessedDevice(GetStateRequest device) {
        devicesProcessed.put(device.getToken(), device);
        notifyAll();
    }

    @Override
    public synchronized Set<String> getWaitDeviceAndWaitRequestWithEqualsToken() {
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
