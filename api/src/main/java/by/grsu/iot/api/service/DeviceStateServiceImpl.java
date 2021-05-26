package by.grsu.iot.api.service;

import by.grsu.iot.api.model.dto.thing.device.DeviceState;
import by.grsu.iot.api.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.api.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.api.model.exception.ConflictException;
import by.grsu.iot.api.repository.sql.DeviceStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DeviceStateServiceImpl implements DeviceStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStateServiceImpl.class);

    private final DeviceStateRepository repository;
    private final StateRequestConsumer stateRequestConsumer;

    @Value("${by.grsu.iot.service.device.state.get.long-polling.time}")
    private Long DEVICE_LONG_POLLING_TIME;

    @Value("${by.grsu.iot.service.device.state.set.long-polling.time}")
    private Long REQUEST_LONG_POLLING_TIME;

    public DeviceStateServiceImpl(
            DeviceStateRepository repository,
            StateRequestConsumer stateRequestConsumer
    ) {
        this.stateRequestConsumer = stateRequestConsumer;

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
        return DEVICE_LONG_POLLING_TIME;
    }

    @Override
    public Long getRequestWaitTime() {
        return REQUEST_LONG_POLLING_TIME;
    }
}