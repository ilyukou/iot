package by.grsu.iot.api.repository.sql;

import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.model.sql.Sensor;
import by.grsu.iot.api.repository.EntityFactory;
import by.grsu.iot.api.repository.sql.jpa.SensorJpaRepository;
import by.grsu.iot.api.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class SensorRepositoryImpl implements SensorRepository {

    private final SensorJpaRepository sensorJpaRepository;
    private final ProjectRepository projectRepository;
    private final EntityFactory entityFactory;

    @Lazy
    public SensorRepositoryImpl(
            SensorJpaRepository sensorJpaRepository,
            ProjectRepository projectRepository,
            EntityFactory entityFactory) {
        this.sensorJpaRepository = sensorJpaRepository;
        this.projectRepository = projectRepository;
        this.entityFactory = entityFactory;
    }

    @Override
    public String getOwnerUsername(Long sensorId) {
        Long project = sensorJpaRepository.findProjectId(sensorId);

        return projectRepository.getProjectOwnerUsername(project);
    }

    @Override
    public Sensor create(Long project, String name) {
        Project p = projectRepository.getById(project);

        Sensor sensor = entityFactory.createSensor();
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

    @Override
    public String getTokenById(Long id) {
        return sensorJpaRepository.findTokenById(id);
    }

    @Override
    public Page<Sensor> getPage(Project project, Pageable pageable) {
        return sensorJpaRepository.findAllByProject(project, pageable);
    }

    @Override
    public boolean hasUserOwnerSensor(String username, Long sensorId) {
        return username.equals(getOwnerUsername(sensorId));
    }
}
