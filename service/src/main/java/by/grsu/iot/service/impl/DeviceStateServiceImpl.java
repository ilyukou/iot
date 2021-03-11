package by.grsu.iot.service.impl;

import by.grsu.iot.model.elastic.DeviceStateElasticsearch;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.DeviceStateQueueRepository;
import by.grsu.iot.service.domain.DeviceState;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import by.grsu.iot.service.interf.DeviceStateService;
import by.grsu.iot.service.validation.factory.DataBinderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:application-service.properties")
@Service
public class DeviceStateServiceImpl implements DeviceStateService {

    private final DeviceRepository deviceRepository;
    @Value("${device.state.set.long-polling.time}")
    private Long DEVICE_SET_TIME;
    @Value("${device.state.get.long-polling.time}")
    private Long DEVICE_GET_TIME;

    private final DeviceStateQueueRepository deviceStateQueueRepository;
    private final DeviceCrudService deviceCrudService;
    @Value("${device.state.check.repeat.time}")
    private Long DEVICE_WAITING_TIME;
    private final DataBinderFactory dataBinderFactory;

    public DeviceStateServiceImpl(
            DeviceStateQueueRepository deviceStateQueueRepository,
            DeviceCrudService deviceCrudService,
            DeviceRepository deviceRepository,
            DataBinderFactory dataBinderFactory
    ) {
        this.deviceStateQueueRepository = deviceStateQueueRepository;
        this.deviceCrudService = deviceCrudService;
        this.deviceRepository = deviceRepository;
        this.dataBinderFactory = dataBinderFactory;
    }

    @Override
    public DeviceState getState(String remoteState, String token) {
        Device device = deviceCrudService.getByToken(token);

        if (deviceStateQueueRepository.isExist(token)){
            DeviceStateElasticsearch elasticsearch = deviceStateQueueRepository.getAndDelete(token);
            device.setState(elasticsearch.getState());

            return new DeviceState(deviceRepository.update(device));
        }

        if (!device.getState().equals(remoteState)){
            return new DeviceState(device);
        }

        Date date = new Date();
        while (new Date().getTime() < date.getTime() + DEVICE_GET_TIME){

            if (deviceStateQueueRepository.isExist(token)){
                DeviceStateElasticsearch elasticsearch = deviceStateQueueRepository.getAndDelete(token);
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
        validate(newState, device);

        if (deviceStateQueueRepository.isExist(token)){
            deviceStateQueueRepository.delete(token);
        }

        if(newState.equals(device.getState())){
            throw new BadRequestException("state", "This state is already set");
        }
        
        deviceStateQueueRepository.put(new DeviceStateElasticsearch(token, newState, new Date().getTime()));

        Date date = new Date();
        while (new Date().getTime() < date.getTime() + DEVICE_SET_TIME){

            if (!deviceStateQueueRepository.isExist(token)){
                return new DeviceState(newState);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(DEVICE_WAITING_TIME);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        deviceStateQueueRepository.delete(token);
        Device check = deviceCrudService.getByToken(token);

        if (check.getState().equals(device.getState())){
            return null;
        }

        if (check.getState().equals(newState)){
            return new DeviceState(newState);
        }

        throw new ConcurrentModificationException();
    }

    private void validate(String state, Device device){
        DataBinder dataBinder = dataBinderFactory.createDataBinder(new DeviceState(state));
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ExceptionUtil.throwException(dataBinder.getBindingResult());
        }

        if (!device.getStates().contains(state)){
            throw new BadRequestException("state", "State not contains in state=" + device.getStates());
        }
    }
}
