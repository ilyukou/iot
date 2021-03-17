package by.grsu.iot.repository.mongo;

import by.grsu.iot.model.domain.DeviceStateResult;
import by.grsu.iot.model.mongo.DeviceStateMongoDocument;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceStateMongoRepositoryImpl implements DeviceStateMongoRepository {

    private final DeviceStateMongo deviceStateMongo;

    public DeviceStateMongoRepositoryImpl(DeviceStateMongo deviceStateMongo) {
        this.deviceStateMongo = deviceStateMongo;
    }

    @Override
    public void put(DeviceStateMongoDocument deviceStateMongoDocument) {
        deviceStateMongo.save(deviceStateMongoDocument);
    }

    @Override
    public DeviceStateMongoDocument get(String token) {
        return deviceStateMongo.findDeviceStateMongoDocumentByToken(token);
    }

    @Override
    public boolean isExist(String token) {
        return deviceStateMongo.existsByToken(token);
    }

    @Override
    public boolean isWait(String token) {
        return deviceStateMongo
                .findDeviceStateMongoDocumentByTokenAndResult(token, DeviceStateResult.WAIT);
    }

    @Override
    public boolean isCompleted(String token) {
        return deviceStateMongo
                .findDeviceStateMongoDocumentByTokenAndResult(token, DeviceStateResult.COMPLETED);
    }

    @Override
    public void delete(String token) {
        deviceStateMongo.deleteById(get(token).getId());
    }

    @Override
    public DeviceStateMongoDocument getAndDelete(String token) {
        DeviceStateMongoDocument document = get(token);

        delete(token);

        return document;
    }
}
