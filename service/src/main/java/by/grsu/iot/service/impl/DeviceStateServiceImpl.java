package by.grsu.iot.service.impl;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.model.domain.DeviceStateResult;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.DeviceStateRepository;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.interf.DeviceStateService;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:application-service.properties")
@Service
public class DeviceStateServiceImpl implements DeviceStateService {

    @Value("${device.state.set.long-polling.time}")
    private Long DEVICE_SET_TIME;

    @Value("${device.state.get.long-polling.time}")
    private Long DEVICE_GET_TIME;

    @Value("${device.state.check.repeat.time}")
    private Long DEVICE_WAITING_TIME;

    private final DeviceStateRepository deviceStateRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceCrudService deviceCrudService;


    public DeviceStateServiceImpl(
            DeviceStateRepository deviceStateRepository,
            DeviceCrudService deviceCrudService,
            DeviceRepository deviceRepository
    ) {
        this.deviceStateRepository = deviceStateRepository;
        this.deviceCrudService = deviceCrudService;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceState getState(String remoteState, String token) {
        Device device = deviceCrudService.getByToken(token);

        // In Queue exist request for changing state
        if (deviceStateRepository.isExist(token)){
            DeviceState elasticsearch = deviceStateRepository.getAndDelete(token);
            device.setState(elasticsearch.getState());

            return new DeviceState(deviceRepository.update(device));
        }

        if (!device.getState().equals(remoteState)){
            return new DeviceState(device);
        }

        Date date = new Date();
        while (new Date().getTime() < date.getTime() + DEVICE_GET_TIME){

            if (deviceStateRepository.isExist(token)){
                DeviceState elasticsearch = deviceStateRepository.getAndDelete(token);
                device.setState(elasticsearch.getState());

                return new DeviceState(deviceRepository.update(device));
            }

            try {
                TimeUnit.MILLISECONDS.sleep(DEVICE_WAITING_TIME);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        return null;
    }

    @Override
    public DeviceState setState(String newState, String token) {
        Device device = deviceCrudService.getByToken(token);

        if (deviceStateRepository.isExist(token)){
            throw new BadRequestException("Another request in the queue");
        }

        if(newState.equals(device.getState())){
            throw new BadRequestException("state", "This state is already set");
        }

        deviceStateRepository.put(new DeviceState(token, newState, new Date().getTime(), DeviceStateResult.WAIT));

        Date date = new Date();
        while (new Date().getTime() < date.getTime() + DEVICE_SET_TIME){

            if (!deviceStateRepository.isExist(token)){
                return new DeviceState(newState);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(DEVICE_WAITING_TIME);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        deviceStateRepository.delete(token);
        Device check = deviceCrudService.getByToken(token);

        if (check.getState().equals(device.getState())){
            return null;
        }

        if (check.getState().equals(newState)){
            return new DeviceState(newState);
        }

        throw new ConcurrentModificationException();
    }
}
