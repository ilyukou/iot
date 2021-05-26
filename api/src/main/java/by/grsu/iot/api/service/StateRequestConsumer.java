package by.grsu.iot.api.service;


import by.grsu.iot.api.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.api.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.api.repository.sql.DeviceStateRepository;
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
            for (String token : repository.getWaitGetStateRequestAndWaitSetDeviceRequestWithEqualsToken()) {
                GetStateRequest device = repository.getWaitGetStateRequest(token);
                SetDeviceRequest request = repository.getWaitSetDeviceRequest(token);

                device.setState(request.getState());
                LOGGER.trace("Device and request connected " + token);

                repository.removeWaitGetStateRequest(token);
                repository.removeWaitSetDeviceRequest(token);

                repository.putSetDeviceRequest(request);
                repository.putProcessedGetStateRequest(device);

                synchronized (deviceStateService) {
                    deviceStateService.notifyAll();
                }
            }
        }
    }
}
