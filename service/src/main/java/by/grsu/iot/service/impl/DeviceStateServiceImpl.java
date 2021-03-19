package by.grsu.iot.service.impl;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.service.impl.concurrent.DeviceStateConcurrentRepository;
import by.grsu.iot.service.impl.folder.StateConsumer;
import by.grsu.iot.service.impl.model.DeviceStateDevice;
import by.grsu.iot.service.impl.model.DeviceStateRequest;
import by.grsu.iot.service.interf.DeviceStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@PropertySource("classpath:application-service.properties")
@Service
@Scope(value="singleton")
public class DeviceStateServiceImpl implements DeviceStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStateServiceImpl.class);

    private final DeviceStateConcurrentRepository repository;
    private final StateConsumer stateConsumer;


    public DeviceStateServiceImpl(
            DeviceStateConcurrentRepository deviceStateConcurrentRepository,
            StateConsumer stateConsumer
    ) {
        this.stateConsumer = stateConsumer;

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        service.execute(stateConsumer);

        this.repository = deviceStateConcurrentRepository;
    }

    @Override
    public DeviceState getState(String remoteState, String token) {
        repository.putWaitDevice(new DeviceStateDevice(token, remoteState));

//        synchronized (stateConsumer){
//            stateConsumer.notify();
//        }

        LOGGER.info("[GET] stateConsumer notify " + token);

        DeviceStateDevice device = repository.getProcessedDevice(token);

        LOGGER.info("[GET] Processed device " + device);

        return new DeviceState(device.getToken(), device.getState());

    }

    @Override
    public DeviceState setState(String newState, String token)  {
        repository.putWaitRequest(new DeviceStateRequest(token, newState));

//        synchronized (stateConsumer){
//            stateConsumer.notify();
//        }

        LOGGER.info("[SET] stateConsumer notify " + token);

        DeviceStateRequest request = repository.getProcessedRequest(token);

        LOGGER.info("[SET] Processed request " + request);

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
}