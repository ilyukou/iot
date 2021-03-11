package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.jpa.DeviceJpaRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceJpaRepository deviceJpaRepository;
    private final ProjectRepository projectRepository;
    private final EntityFactory entityFactory;

    public DeviceRepositoryImpl(DeviceJpaRepository deviceJpaRepository, @Lazy ProjectRepository projectRepository, EntityFactory entityFactory) {
        this.deviceJpaRepository = deviceJpaRepository;
        this.projectRepository = projectRepository;
        this.entityFactory = entityFactory;
    }

    @Override
    public Device create(final Project project, final Device device) {
        Project p = SerializationUtils.clone(project);
        Device d = SerializationUtils.clone(device);

        Device thing = entityFactory.createDevice();
        thing.setName(d.getName());
        thing.setStates(d.getStates());
        thing.setState(d.getState());
        thing.setProject(p);

        Set<Device> things = new HashSet<>();

        if (p.getDevices() != null && p.getDevices().size() > 0) {
            things = p.getDevices();
        }

        things.add(thing);
        p.setDevices(things);

        thing = deviceJpaRepository.save(thing);

        projectRepository.update(p);

        return thing;
    }

    @Override
    public Device getById(final Long id) {
        return deviceJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Device> getDevicesByProject(final Project project) {
        Project p = SerializationUtils.clone(project);

        return deviceJpaRepository.findDevicesByProject(p).orElse(new HashSet<>());
    }

    @Override
    public Device update(final Device device) {
        Device d = SerializationUtils.clone(device);

        return deviceJpaRepository.save(d);
    }

    @Override
    public Device getByToken(final String token) {
        return deviceJpaRepository.findDeviceByToken(token).orElse(null);
    }

    @Override
    public boolean delete(final Long id) {
        if(!isExist(id)){
            return false;
        }

        Device device = getById(id);

        Project project = projectRepository.getById(device.getProject().getId());
        Set<Device> devices = project.getDevices();

        devices.remove(device);

        project.setDevices(devices);

        projectRepository.update(project);

        deviceJpaRepository.deleteById(device.getId());

        return !isExist(device.getId());
    }

    @Override
    public boolean isExist(final Long id) {
        return deviceJpaRepository.existsById(id);
    }

    @Override
    public String getDeviceOwnerUsername(Long device) {
        Long project = deviceJpaRepository.findProjectId(device);

        return projectRepository.getProjectOwnerUsername(project);
    }
}
