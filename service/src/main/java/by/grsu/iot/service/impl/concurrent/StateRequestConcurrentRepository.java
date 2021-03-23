package by.grsu.iot.service.impl.concurrent;

import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.service.domain.state.GetStateRequest;
import by.grsu.iot.service.domain.state.SetDeviceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(value="singleton")
public class StateRequestConcurrentRepository extends Thread  {

    private final static Logger LOGGER = LoggerFactory.getLogger(StateRequestConcurrentRepository.class);

    private final DeviceRepository deviceRepository;

    private Map<String, GetStateRequest> devices = new ConcurrentHashMap<>();
    private Map<String, SetDeviceRequest> requests = new ConcurrentHashMap<>();

    private Map<String, SetDeviceRequest> requestsProcessed = new ConcurrentHashMap<>();
    private Map<String, GetStateRequest> devicesProcessed = new ConcurrentHashMap<>();

    public StateRequestConcurrentRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public synchronized GetStateRequest getWaitDevice(String token){
        return devices.get(token);
    }

    public synchronized void removeWaitDevice(String token){
        devices.remove(token);
    }

    public synchronized void putWaitDevice(GetStateRequest device){
        devices.put(device.getToken(), device);
        LOGGER.info("[GET] New device " + device);
        LOGGER.info("requests=" + requests.size() + "; devices=" + devices.size());
        notifyAll();
    }

    public synchronized SetDeviceRequest getWaitRequest(String token){
        return requests.get(token);
    }

    public synchronized void removeWaitRequest(String token){
        requests.remove(token);
    }

    public synchronized void putWaitRequest(SetDeviceRequest request){
        requests.put(request.getToken(), request);
        LOGGER.info("[SET] New request " + request);
        LOGGER.info("requests=" + requests.size() + "; devices=" + devices.size());
        notifyAll();
    }

    public synchronized SetDeviceRequest getProcessedRequest(String token){
        while (!isExistProcessedRequest(token)){
            try {
                wait();
            }
            catch (InterruptedException e) {
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

    public synchronized void removeProcessedRequest(String token){
        requestsProcessed.remove(token);
    }

    public synchronized boolean isExistProcessedRequest(String token){
        return requestsProcessed.containsKey(token);
    }

    public synchronized void putProcessedRequest(SetDeviceRequest request){
        requestsProcessed.put(request.getToken(), request);
        notifyAll();
    }

    //    -------------

    public synchronized GetStateRequest getProcessedDevice(String token){
        while (!isExistProcessedDevice(token)){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GetStateRequest device = devicesProcessed.get(token);
        devicesProcessed.remove(token);
        devices.remove(token);

        notifyAll();
        return device;
    }

    public synchronized void removeProcessedDevice(String token){
        devicesProcessed.remove(token);
    }

    public synchronized boolean isExistProcessedDevice(String token){
        return devicesProcessed.containsKey(token);
    }

    public synchronized void putProcessedDevice(GetStateRequest device){
        devicesProcessed.put(device.getToken(), device);
        notifyAll();
    }

    public synchronized Set<String> getWaitDeviceAndWaitRequestWithEqualsToken(){
        if (devices.size() == 0 || requests.size() == 0){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<String> keys;

        if (devices.size() > requests.size()){
            keys = getWaitDeviceAndWaitRequestWithEqualsToken(requests.keySet(), devices.keySet());

        } else {
            keys = getWaitDeviceAndWaitRequestWithEqualsToken(devices.keySet(), requests.keySet());
        }

        if (keys.size() == 0){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return keys;
    }

    private Set<String> getWaitDeviceAndWaitRequestWithEqualsToken(Set<String> smallSet, Set<String> bigSet){
        Set<String> result = new LinkedHashSet<>();

        for (String value : smallSet){
            if (bigSet.contains(value)){
                result.add(value);
            }
        }

        return result;
    }
}
