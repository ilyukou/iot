package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.jpa.DeviceJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceJpaRepository deviceJpaRepository;
    private final ProjectRepository projectRepository;
    private final TimeUtil timeUtil;

    public DeviceRepositoryImpl(DeviceJpaRepository deviceJpaRepository, ProjectRepository projectRepository, TimeUtil timeUtil) {
        this.deviceJpaRepository = deviceJpaRepository;
        this.projectRepository = projectRepository;
        this.timeUtil = timeUtil;
    }

    @Override
    public Device create(Project project, String name) {
        Device sensor = EntityFactory.createDevice();
        sensor.setName(name);
        sensor.setProject(project);

        Set<Device> sensors = new HashSet<>();

        if (project.getDevices() != null && project.getDevices().size() > 0) {
            sensors = project.getDevices();
        }

        sensors.add(sensor);
        project.setDevices(sensors);

        sensor = deviceJpaRepository.save(sensor);

        projectRepository.update(project);

        return sensor;
    }

    @Override
    public Device getById(Long id) {
        return deviceJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Device update(Device device) {
        return deviceJpaRepository.save(device);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Device getByToken(String token) {
        return deviceJpaRepository.findDeviceByToken(token).orElse(null);
    }

    @Override
    public boolean isExist(Long id) {
        return deviceJpaRepository.existsById(id);
    }

    @Override
    public boolean isExist(String token) {
        return getByToken(token) != null;
    }
}
