package by.grsu.iot.repository.impl;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.model.mongo.DeviceStateMongoDocument;
import by.grsu.iot.repository.interf.DeviceStateRepository;
import by.grsu.iot.repository.mongo.DeviceStateMongoRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceStateRepositoryImpl implements DeviceStateRepository {

    private final DeviceStateMongoRepository deviceStateMongoRepository;

    public DeviceStateRepositoryImpl(DeviceStateMongoRepository deviceStateMongoRepository) {
        this.deviceStateMongoRepository = deviceStateMongoRepository;
    }

    @Override
    public void put(DeviceState deviceState) {
        deviceStateMongoRepository.put(new DeviceStateMongoDocument(deviceState));
    }

    @Override
    public DeviceState get(String token) {
        return new DeviceState(deviceStateMongoRepository.get(token));
    }

    @Override
    public boolean isExist(String token) {
        return deviceStateMongoRepository.isExist(token);
    }

    @Override
    public boolean isWait(String token) {
        return deviceStateMongoRepository.isWait(token);
    }

    @Override
    public boolean isCompleted(String token) {
        return deviceStateMongoRepository.isCompleted(token);
    }

    @Override
    public void delete(String token) {
        deviceStateMongoRepository.delete(token);
    }

    @Override
    public DeviceState getAndDelete(String token) {
        return new DeviceState(deviceStateMongoRepository.getAndDelete(token));
    }
}
