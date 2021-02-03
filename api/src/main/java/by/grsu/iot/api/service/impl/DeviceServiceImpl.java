package by.grsu.iot.api.service.impl;

import by.grsu.iot.api.dto.DeviceState;
import by.grsu.iot.api.exception.EntityNotFoundException;
import by.grsu.iot.api.service.interf.DeviceService;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ProjectRepository projectRepository) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public Device create(Long projectId, String name, String username) {
        Project project = projectRepository.getById(projectId);

        if (!project.getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.warn(ms);
            throw new IllegalArgumentException(ms);
        }

        return deviceRepository.create(project, name);
    }

    @Override
    public Device getById(Long id, String username) {
        Device sensor = deviceRepository.getById(id);

        if (sensor == null || !sensor.isActive()) {
            String ms = "Sensor does not exist with given id {" + id + "}";
            LOG.warn(ms);
            throw new EntityNotFoundException(ms);
        }

        // FIXME - add LAZY init from db
        if (!sensor.getProject().getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.warn(ms);
            throw new IllegalArgumentException(ms);
        }

        return sensor;
    }

    @Override
    public boolean deleteById(Long id, String username) {
        Device device = deviceRepository.getById(id);

        if (device == null || !device.isActive()) {
            String ms = "Sensor does not exist with given id {" + id + "}";
            LOG.warn(ms);
            throw new EntityNotFoundException(ms);
        }

        // FIXME - add LAZY init from db
        if (!device.getProject().getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.warn(ms);
            throw new IllegalArgumentException(ms);
        }

        deviceRepository.delete(id);

        return true;
    }

    @Override
    public void update(Long id, String name, String state, String username) {
        Device device = getById(id, username);

        if (device == null || !device.isActive()) {
            String ms = "Sensor does not exist with given id {" + id + "}";
            LOG.warn(ms);
            throw new EntityNotFoundException(ms);
        }

        // FIXME - add LAZY init from db
        if (!device.getProject().getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.warn(ms);
            throw new IllegalArgumentException(ms);
        }

        if (name != null) {
            device.setName(name);
        }

        if (state != null && device.getStates().contains(state)) {
            device.setState(name);
        }

        update(device);
    }

    @Override
    public Device update(Device device) {
        return deviceRepository.update(device);
    }

    @Override
    public void setState(String token, String state) {
        Device device = deviceRepository.getByToken(token);

        if (device != null && device.getStates().contains(state)) {
            device.setState(state);
            update(device);
        }
    }

    @Override
    public Device getByToken(String token) {
        return deviceRepository.getByToken(token);
    }

    @Override
    public DeviceState getStateWhenDeviceStateNotEqualState(String token, String deviceState) {
        while (true) {
            Device device = getByToken(token);

            if (device == null) {
                LOG.info("Sensor is null");
                return null;
            }

            if (!device.getState().equals(deviceState)) {
                LOG.info("Device state not equal db state");
                return new DeviceState(device);
            }

            try {
                TimeUnit.SECONDS.sleep(1l);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
