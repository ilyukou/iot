package by.grsu.iot.service.impl;

import by.grsu.iot.model.dto.thing.device.DeviceState;
import by.grsu.iot.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.model.exception.ConflictException;
import by.grsu.iot.repository.interf.DeviceStateRepository;
import by.grsu.iot.service.interf.DeviceStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@PropertySource("classpath:application-service.properties")
@Service
public class DeviceStateServiceImpl implements DeviceStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStateServiceImpl.class);

    private final DeviceStateRepository repository;
    private final StateRequestConsumer stateRequestConsumer;
    private static final String MODULE = "by.grsu.iot.service.";
    private static final String DEVICE_LONG_POLLING_TIME = MODULE + "device.state.get.long-polling.time";
    private static final String REQUEST_LONG_POLLING_TIME = MODULE + "device.state.set.long-polling.time";
    private final Environment environment;

    public DeviceStateServiceImpl(
            DeviceStateRepository repository,
            StateRequestConsumer stateRequestConsumer,
            Environment environment
    ) {
        this.stateRequestConsumer = stateRequestConsumer;
        this.environment = environment;

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        service.execute(stateRequestConsumer);

        this.repository = repository;
    }

    @Override
    public DeviceState getState(String token) {
        if (repository.isExistWaitGetStateRequest(token)) {
            String ms = "Device with such token exist";
            LOGGER.trace(ms);
            throw new ConflictException(ms);
        }

        repository.putWaitGetStateRequest(new GetStateRequest(token));

        GetStateRequest device = repository.getProcessedGetStateRequest(token);

        return new DeviceState(device.getToken(), device.getState());

    }

    @Override
    public DeviceState setState(String newState, String token) {
        if (repository.isExistWaitSetDeviceRequest(token)) {
            String ms = "Request with such token exist";
            LOGGER.trace(ms);
            throw new ConflictException(ms);
        }

        repository.putWaitSetDeviceRequest(new SetDeviceRequest(token, newState));

        SetDeviceRequest request = repository.getProcessedSetDeviceRequest(token);

        return new DeviceState(request.getToken(), request.getState());
    }

    @Override
    public void removeDevice(String token) {
        repository.removeWaitGetStateRequest(token);
        repository.removeProcessedGetStateRequest(token);
    }

    @Override
    public void removeRequest(String token) {
        repository.removeWaitSetDeviceRequest(token);
        repository.removeProcessedSetDeviceRequest(token);
    }

    @Override
    public Long getDeviceWaitTime() {
        return Long.valueOf(Objects.requireNonNull(environment.getProperty(DEVICE_LONG_POLLING_TIME)));
    }

    @Override
    public Long getRequestWaitTime() {
        return Long.valueOf(Objects.requireNonNull(environment.getProperty(REQUEST_LONG_POLLING_TIME)));
    }
}