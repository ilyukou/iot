package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elastic.DeviceStateElasticsearch;
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
    public void put(DeviceStateElasticsearch deviceState) {
        deviceStateElasticsearchRepository.save(deviceState);
    }

    @Override
    public DeviceStateElasticsearch get(String deviceToken) {
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
    public DeviceStateElasticsearch getAndDelete(String deviceToken) {
        DeviceStateElasticsearch deviceState = get(deviceToken);

        if(deviceState != null){
            delete(deviceState.getToken());
        }

        return deviceState;
    }
}
