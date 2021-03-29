package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Sensor;

import java.util.List;

public interface SensorRepository {
    String getOwnerUsername(Long sensorId);

    Sensor create(Long project, String name);

    Sensor getById(Long id);

    boolean delete(Long id);

    Sensor update(Sensor sensor);

    boolean isExist(final Long id);

    boolean isExist(String token);

    Sensor getByToken(String token);

    List<Long> getProjectSensorIds(Long projectId);

    Integer getSensorsSize(Long projectId);
}
