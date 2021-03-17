package by.grsu.iot.repository.mongo;

import by.grsu.iot.model.mongo.DeviceStateMongoDocument;

public interface DeviceStateMongoRepository {

    void put(DeviceStateMongoDocument deviceStateMongoDocument);

    DeviceStateMongoDocument get(String token);

    boolean isExist(String token);

    boolean isWait(String token);

    boolean isCompleted(String token);

    void delete(String token);

    DeviceStateMongoDocument getAndDelete(String token);
}
