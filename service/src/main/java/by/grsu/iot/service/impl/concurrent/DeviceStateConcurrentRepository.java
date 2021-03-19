package by.grsu.iot.service.impl.concurrent;

import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.service.impl.model.DeviceStateDevice;
import by.grsu.iot.service.impl.model.DeviceStateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Tunnel
@Service
@Scope(value="singleton")
public class DeviceStateConcurrentRepository extends Thread  {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeviceStateConcurrentRepository.class);

    private final DeviceRepository deviceRepository;

    private Map<String, DeviceStateDevice> devices = new ConcurrentHashMap<>();
    private Map<String, DeviceStateRequest> requests = new ConcurrentHashMap<>();

    private Map<String, DeviceStateRequest> requestsProcessed = new ConcurrentHashMap<>();
    private Map<String, DeviceStateDevice> devicesProcessed = new ConcurrentHashMap<>();

    public DeviceStateConcurrentRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public synchronized DeviceStateDevice getWaitDevice(String token){
        return devices.get(token);
    }

    public synchronized void removeWaitDevice(String token){
        devices.remove(token);
    }

    public synchronized boolean isExistWaitDevice(String token){
        return devices.containsKey(token);
    }

    public synchronized boolean isExistWaitDevice(){
        return devices.size() > 0;
    }

    public synchronized Set<String> getWaitDeviceKeys(){
        return devices.keySet();
    }

    public synchronized void putWaitDevice(DeviceStateDevice device){
        devices.put(device.getToken(), device);
        notifyAll();
        LOGGER.info("[GET] notifyAll");
    }

//    -------------
    public synchronized DeviceStateRequest getWaitRequest(String token){
        return requests.get(token);
    }

    public synchronized Set<String> getWaitRequestKeys(){
        return requests.keySet();
    }

    public synchronized void removeWaitRequest(String token){
        requests.remove(token);
    }

    public synchronized boolean isExistWaitRequest(String token){
        return requests.containsKey(token);
    }

    public synchronized boolean isExistWaitRequest(){
        return requests.size() > 0;
    }

    public synchronized void putWaitRequest(DeviceStateRequest request){
        requests.put(request.getToken(), request);
        notifyAll();
        LOGGER.info("[SET] notifyAll");
    }

//    -------------
    public synchronized DeviceStateRequest getProcessedRequest(String token){
        while (!isExistProcessedRequest(token)){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DeviceStateRequest request = requestsProcessed.get(token);
        requestsProcessed.remove(token);

        deviceRepository.changeState(request.getState(), request.getToken());

        return request;
    }

    public synchronized void removeProcessedRequest(String token){
        requestsProcessed.remove(token);
    }

    public synchronized boolean isExistProcessedRequest(String token){
        return requestsProcessed.containsKey(token);
    }

    public synchronized void putProcessedRequest(DeviceStateRequest request){
        requestsProcessed.put(request.getToken(), request);
        notifyAll();
    }

    //    -------------

    public synchronized DeviceStateDevice getProcessedDevice(String token){
        while (!isExistProcessedDevice(token)){
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DeviceStateDevice device = devicesProcessed.get(token);
        devicesProcessed.remove(token);

        deviceRepository.changeState(device.getState(), device.getToken());

        return device;
    }

    public synchronized void removeProcessedDevice(String token){
        devicesProcessed.remove(token);
    }

    public synchronized boolean isExistProcessedDevice(String token){
        return devicesProcessed.containsKey(token);
    }

    public synchronized void putProcessedDevice(DeviceStateDevice device){
        devicesProcessed.put(device.getToken(), device);
        notify();
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
