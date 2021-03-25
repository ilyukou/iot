package by.grsu.iot.service.impl;


import by.grsu.iot.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.repository.interf.DeviceStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Consumer that get wait device and wait request from repository and put processed device
 * and request into repository
 */
@Component
public class StateRequestConsumer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateRequestConsumer.class);

    private final DeviceStateRepository repository;
    private final DeviceStateServiceImpl deviceStateService;

    // circular dependency
    @Lazy
    public StateRequestConsumer(DeviceStateRepository deviceStateRepository, DeviceStateServiceImpl deviceStateService) {
        this.repository = deviceStateRepository;
        this.deviceStateService = deviceStateService;
    }

    @Override
    public void run() {
        while (true) {
            LOGGER.trace("Thread consumer run " + Thread.currentThread().getName());
            for (String token : repository.getWaitDeviceAndWaitRequestWithEqualsToken()) {
                GetStateRequest device = repository.getWaitDevice(token);
                SetDeviceRequest request = repository.getWaitRequest(token);

                device.setState(request.getState());
                LOGGER.trace("Device and request connected " + token);

                repository.removeWaitDevice(token);
                repository.removeWaitRequest(token);

                repository.putProcessedRequest(request);
                repository.putProcessedDevice(device);

                synchronized (deviceStateService) {
                    deviceStateService.notifyAll();
                }
            }
        }
    }
}
