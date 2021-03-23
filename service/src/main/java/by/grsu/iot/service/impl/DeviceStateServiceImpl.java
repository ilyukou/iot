package by.grsu.iot.service.impl;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.service.impl.concurrent.StateRequestConcurrentRepository;
import by.grsu.iot.service.impl.concurrent.StateRequestConsumer;
import by.grsu.iot.service.domain.state.GetStateRequest;
import by.grsu.iot.service.domain.state.SetDeviceRequest;
import by.grsu.iot.service.interf.DeviceStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@PropertySource("classpath:application-service.properties")
@Service
//@Scope(value="singleton")
public class DeviceStateServiceImpl implements DeviceStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStateServiceImpl.class);

    private final StateRequestConcurrentRepository repository;
    private final StateRequestConsumer stateRequestConsumer;

    @Value("${device.state.get.long-polling.time}")
    private Long deviceWaitTime;

    @Value("${device.state.set.long-polling.time}")
    private Long requestWaitTime;

    public DeviceStateServiceImpl(
            StateRequestConcurrentRepository stateRequestConcurrentRepository,
            StateRequestConsumer stateRequestConsumer
    ) {
        this.stateRequestConsumer = stateRequestConsumer;

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        service.execute(stateRequestConsumer);

        this.repository = stateRequestConcurrentRepository;
    }

    @Override
    public DeviceState getState(String token) {
        repository.putWaitDevice(new GetStateRequest(token));

        GetStateRequest device = repository.getProcessedDevice(token);

        return new DeviceState(device.getToken(), device.getState());

    }

    @Override
    public DeviceState setState(String newState, String token)  {
        repository.putWaitRequest(new SetDeviceRequest(token, newState));

        SetDeviceRequest request = repository.getProcessedRequest(token);

        return new DeviceState(request.getToken(), request.getState());
    }

    @Override
    public void removeDevice(String token) {
        repository.removeWaitDevice(token);
        repository.removeProcessedDevice(token);
    }

    @Override
    public void removeRequest(String token) {
        repository.removeWaitRequest(token);
        repository.removeProcessedRequest(token);
    }

    @Override
    public Long getDeviceWaitTime() {
        return deviceWaitTime;
    }

    @Override
    public Long getRequestWaitTime() {
        return requestWaitTime;
    }
}