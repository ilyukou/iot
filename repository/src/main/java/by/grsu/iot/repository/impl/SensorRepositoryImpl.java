package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.repository.jpa.SensorJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class SensorRepositoryImpl implements SensorRepository {

    private final SensorJpaRepository sensorJpaRepository;
    private final ProjectRepository projectRepository;

    @Lazy
    public SensorRepositoryImpl(
            SensorJpaRepository sensorJpaRepository,
            ProjectRepository projectRepository
    ) {
        this.sensorJpaRepository = sensorJpaRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public String getOwnerUsername(Long sensorId) {
        Long project = sensorJpaRepository.findProjectId(sensorId);

        return projectRepository.getProjectOwnerUsername(project);
    }

    @Override
    public Sensor create(Long project, String name) {
        Project p = projectRepository.getById(project);

        Sensor sensor = EntityFactory.createSensor();
        sensor.setName(name);
        sensor.setProject(p);

        return sensorJpaRepository.save(sensor);
    }

    @Override
    public Sensor getById(Long id) {
        return sensorJpaRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        if (!isExist(id)) {
            return false;
        }

        sensorJpaRepository.deleteById(id);

        return true;
    }

    @Override
    public Sensor update(Sensor sensor) {
        Sensor s = SerializationUtils.clone(sensor);

        s.setUpdated(TimeUtil.getCurrentDate());

        return sensorJpaRepository.save(s);
    }

    @Override
    public boolean isExist(Long id) {
        return sensorJpaRepository.existsById(id);
    }

    @Override
    public boolean isExist(String token) {
        return getByToken(token) != null;
    }

    @Override
    public Sensor getByToken(String token) {
        return sensorJpaRepository.findSensorByToken(token).orElse(null);
    }

    @Override
    public List<Long> getProjectSensorIds(Long projectId) {
        return sensorJpaRepository.findSensorsIdsByProjectId(projectId);
    }

    @Override
    public Integer getSensorsSize(Long projectId) {
        return sensorJpaRepository.getSensorsSize(projectId);
    }
}
