package by.grsu.iot.service.impl.folder;


import by.grsu.iot.service.impl.DeviceStateServiceImpl;
import by.grsu.iot.service.impl.concurrent.DeviceStateConcurrentRepository;
import by.grsu.iot.service.impl.model.DeviceStateDevice;
import by.grsu.iot.service.impl.model.DeviceStateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Scope(value="singleton")
public class StateConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateConsumer.class);

    private final DeviceStateConcurrentRepository repository;
    private final DeviceStateServiceImpl deviceStateService;

    @Lazy
    public StateConsumer(DeviceStateConcurrentRepository deviceStateConcurrentRepository, DeviceStateServiceImpl deviceStateService) {
        this.repository = deviceStateConcurrentRepository;
        this.deviceStateService = deviceStateService;
    }

    @Override
    public void run() {
        while (true){
            Set<String> tokens = repository.getWaitDeviceAndWaitRequestWithEqualsToken();

            if (tokens.size() > 0){
                LOGGER.info("Thread consumer " + Thread.currentThread().getName());

                for (String token : repository.getWaitRequestKeys()){
                    LOGGER.info("Request size " + repository.getWaitRequestKeys().size());
                    LOGGER.info("Device size " + repository.getWaitDeviceKeys().size());

                    if (repository.isExistWaitDevice(token)){
                        DeviceStateDevice device = repository.getWaitDevice(token);
                        DeviceStateRequest request = repository.getWaitRequest(token);

                        device.setState(request.getState());
                        LOGGER.info("Device and request connected " + token);

                        device.setProcessed(true);
                        request.setProcessed(true);

                        repository.removeWaitDevice(token);
                        repository.removeWaitRequest(token);

                        repository.putProcessedRequest(request);
                        repository.putProcessedDevice(device);

                        synchronized (deviceStateService){
                            deviceStateService.notifyAll();
                        }
                    }
                }
            }
//            else {
//                try {
//                    synchronized (this){
//                        LOGGER.info("Thread wait " + Thread.currentThread().getName());
//                        this.wait();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
