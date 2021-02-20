package by.grsu.iot.service.impl;

import by.grsu.iot.model.elastic.DeviceStateElasticsearch;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.interf.DeviceService;
import by.grsu.iot.service.domain.DeviceState;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.DeviceStateQueueRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;
    private final DeviceStateQueueRepository deviceStateQueueRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, ProjectRepository projectRepository, DeviceStateQueueRepository deviceStateQueueRepository) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
        this.deviceStateQueueRepository = deviceStateQueueRepository;
    }


    @Override
    public Device create(Long projectId, Device device, String username) {
        Project project = projectRepository.getById(projectId);

        if(project == null){
            throw new EntityNotFoundException("Project does not exist with given id={" + projectId + "}");
        }

        if (!project.getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with giver username {" + username + "}");
        }

        if(device.getStates().size() < 2){
            throw new BadRequestException("state", "States size is less than 2");
        }

        if(!device.getStates().contains(device.getState())){
            throw new BadRequestException("state", "State not contains in states");
        }

        return deviceRepository.create(project, device);
    }

    @Override
    public Device getById(Long id, String username) {
        Device deviceFromRep = deviceRepository.getById(id);

        if(deviceFromRep == null){
            throw new EntityNotFoundException("id", "Device does not exist with given id={" + id + "}");
        }

        if (!deviceFromRep.getProject().getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with giver username {" + username + "}");
        }

        return deviceFromRep;
    }

    @Override
    public boolean deleteById(Long id, String username) {
        Device device = deviceRepository.getById(id);

        if(device == null){
            throw new EntityNotFoundException("id", "Device does not exist with given id={" + id + "}");
        }

        if (!device.getProject().getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with giver username {" + username + "}");
        }

        deviceRepository.delete(device.getId());

        return true;
    }

    @Override
    public Device update(Long id, Device device, String username) {
        Device deviceFromRep = deviceRepository.getById(id);

        if(deviceFromRep == null){
            throw new EntityNotFoundException("id", "Device does not exist with given id={" + id + "}");
        }

        if (!deviceFromRep.getProject().getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with giver username {" + username + "}");
        }

        if(device.getStates().size() < 2){
            throw new BadRequestException("state", "States size is less than 2");
        }

        if(!device.getStates().contains(device.getState())){
            throw new BadRequestException("state", "State not contains in states");
        }

        device = SerializationUtils.clone(deviceFromRep.updateField(device));

        return update(device);
    }

    @Override
    public Device update(Device device) {
        return deviceRepository.update(device);
    }

    @Override
    public DeviceState setState(String token, String state) {
        Device device = deviceRepository.getByToken(token);

        if(device == null){
            throw new EntityNotFoundException("Not found device with such token={" + token + "}");
        }

        if(!device.getStates().contains(state)){
            throw new EntityNotFoundException("Not found state={" + state +"}, list of possible " +
                    "states={" + device.getStates() + "}");
        }

        deviceStateQueueRepository.put(new DeviceStateElasticsearch(token, state, new Date().getTime()));

        while (true){

            if(deviceStateQueueRepository.isExist(token)){
                return new DeviceState(deviceRepository.getByToken(token));
            }

            try {
                TimeUnit.SECONDS.sleep(1l);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    @Override
    public Device getByToken(String token) {
        return deviceRepository.getByToken(token);
    }

    @Override
    public DeviceState getStateWhenDeviceStateNotEqualState(String token, String deviceState) {
        Device device = getByToken(token);

        if (device == null) {
            LOG.info("Sensor is null");
            return null;
        }

        if (!device.getState().equals(deviceState)) {
            LOG.info("Device state not equal db state");
            return new DeviceState(device);
        }

        while (true) {

            if(deviceStateQueueRepository.isExist(token)){
                device.setState(deviceStateQueueRepository.getAndDelete(token).getState());

                return new DeviceState(update(device));
            }

            try {
                TimeUnit.SECONDS.sleep(1l);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    @Override
    public void deleteDeviceStateChangeRequests(String token) {
        deviceStateQueueRepository.delete(token);
    }
}
