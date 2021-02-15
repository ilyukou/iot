package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elastic.DeviceState;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.repository.interf.DeviceStateQueueRepository;
import by.grsu.iot.repository.elasticsearchRepository.DeviceStateElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceStateQueueRepositoryImpl implements DeviceStateQueueRepository {

    private final DeviceStateElasticsearchRepository deviceStateElasticsearchRepository;

    public DeviceStateQueueRepositoryImpl(DeviceStateElasticsearchRepository deviceStateElasticsearchRepository) {
        this.deviceStateElasticsearchRepository = deviceStateElasticsearchRepository;
    }

    @Override
    public void put(DeviceState deviceState) {
        deviceStateElasticsearchRepository.save(deviceState);
    }

    @Override
    public DeviceState get(String deviceToken) {
        return deviceStateElasticsearchRepository.findByToken(deviceToken);
    }

    @Override
    public boolean isExist(String deviceToken) {
        return get(deviceToken) != null;
    }

    @Override
    public void delete(String deviceToken) {
        deviceStateElasticsearchRepository.deleteByToken(deviceToken);
    }

    @Override
    public DeviceState getAndDelete(String deviceToken) {
        DeviceState deviceState = get(deviceToken);

        if(deviceState != null){
            delete(deviceState.getToken());
        }

        return deviceState;
    }
}
