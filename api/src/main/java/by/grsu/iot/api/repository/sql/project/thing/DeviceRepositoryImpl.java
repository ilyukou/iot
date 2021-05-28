package by.grsu.iot.api.repository.sql.project.thing;

import by.grsu.iot.api.model.sql.Device;
import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.repository.EntityFactory;
import by.grsu.iot.api.repository.sql.jpa.DeviceJpaRepository;
import by.grsu.iot.api.repository.sql.project.ProjectRepository;
import by.grsu.iot.api.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceJpaRepository deviceJpaRepository;
    private final ProjectRepository projectRepository;

    private final EntityFactory entityFactory;

    public DeviceRepositoryImpl(
            DeviceJpaRepository deviceJpaRepository,
            @Lazy ProjectRepository projectRepository,
            EntityFactory entityFactory) {
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

        return deviceJpaRepository.save(thing);
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
        if (!isExist(id)) {
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

    @Override
    public List<Device> getByIds(List<Long> ids) {
        return deviceJpaRepository.findAllById(ids);
    }

    @Override
    public List<Long> getProjectDeviceIds(Long projectId) {
        return deviceJpaRepository.findDeviceIdsByProjectId(projectId);
    }

    @Override
    public void changeState(String state, String token) {
        deviceJpaRepository.changeState(state, TimeUtil.getCurrentDate(), token);
    }

    @Override
    public String getDeviceStateByToken(String token) {
        return deviceJpaRepository.getDeviceStateByToken(token);
    }

    @Override
    public Integer getDevicesSize(Long projectId) {
        return deviceJpaRepository.getDevicesSize(projectId);
    }

    @Override
    public Page<Device> getPage(Project project, Pageable pageable) {
        return deviceJpaRepository.findAllByProject(project, pageable);
    }

    @Override
    public boolean hasUserOwnerDevice(String username, Long deviceId) {
        return username.equals(getDeviceOwnerUsername(deviceId));
    }
}
