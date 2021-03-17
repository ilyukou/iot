package by.grsu.iot.repository.mongo;

import by.grsu.iot.model.mongo.DeviceStateMongoDocument;
import by.grsu.iot.model.domain.DeviceStateResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStateMongo extends MongoRepository<DeviceStateMongoDocument, String> {

    DeviceStateMongoDocument findDeviceStateMongoDocumentByToken(String token);

    boolean existsByToken(String token);

    boolean findDeviceStateMongoDocumentByTokenAndResult(String token, DeviceStateResult result);
}
