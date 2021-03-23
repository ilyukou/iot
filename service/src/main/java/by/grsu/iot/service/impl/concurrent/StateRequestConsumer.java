package by.grsu.iot.service.impl.concurrent;


import by.grsu.iot.service.impl.DeviceStateServiceImpl;
import by.grsu.iot.service.domain.state.GetStateRequest;
import by.grsu.iot.service.domain.state.SetDeviceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="singleton")
public class StateRequestConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateRequestConsumer.class);

    private final StateRequestConcurrentRepository repository;
    private final DeviceStateServiceImpl deviceStateService;

    @Lazy
    public StateRequestConsumer(StateRequestConcurrentRepository stateRequestConcurrentRepository, DeviceStateServiceImpl deviceStateService) {
        this.repository = stateRequestConcurrentRepository;
        this.deviceStateService = deviceStateService;
    }

    @Override
    public void run() {
        while (true){
            LOGGER.info("Thread consumer " + Thread.currentThread().getName());
            for (String token : repository.getWaitDeviceAndWaitRequestWithEqualsToken()){
                GetStateRequest device = repository.getWaitDevice(token);
                SetDeviceRequest request = repository.getWaitRequest(token);

                device.setState(request.getState());
                LOGGER.info("Device and request connected " + token);

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
}
