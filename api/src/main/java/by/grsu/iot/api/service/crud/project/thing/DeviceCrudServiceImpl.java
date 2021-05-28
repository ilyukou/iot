package by.grsu.iot.api.service.crud.project.thing;

import by.grsu.iot.api.model.annotation.Logging;
import by.grsu.iot.api.model.annotation.Profiling;
import by.grsu.iot.api.model.dto.sort.RequestSortType;
import by.grsu.iot.api.model.dto.thing.device.DeviceForm;
import by.grsu.iot.api.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.api.model.exception.BadRequestApplicationException;
import by.grsu.iot.api.model.sql.Device;
import by.grsu.iot.api.model.util.CollectionUtil;
import by.grsu.iot.api.repository.sql.project.ProjectRepository;
import by.grsu.iot.api.repository.sql.project.thing.DeviceRepository;
import by.grsu.iot.api.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Logging
@Profiling
@Transactional
@Service
public class DeviceCrudServiceImpl implements DeviceCrudService {

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;

    public DeviceCrudServiceImpl(
            DeviceRepository deviceRepository,
            ProjectRepository projectRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public Device create(DeviceForm deviceForm) {

        checkStates(deviceForm.getState(), deviceForm.getStates());

        return deviceRepository.create(projectRepository.getById(deviceForm.getProject()), deviceForm.convert());
    }

    @Override
    public Device getById(Long id) {
        return deviceRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        deviceRepository.delete(id);
    }

    @Override
    public Device update(Long id, DeviceFormUpdate deviceFormUpdate) {
        Device device = deviceRepository.getById(id);

        if (deviceFormUpdate.getStates() != null) {
            checkStates(device.getState(), deviceFormUpdate.getStates());
        }

        device = ObjectUtil.updateField(deviceRepository.getById(id), deviceFormUpdate);

        return deviceRepository.update(device);
    }

    @Override
    public String getDeviceState(String token) {
        return deviceRepository.getDeviceStateByToken(token);
    }

    @Override
    public Page<Device> getPage(Long project, Integer size, Integer page, RequestSortType type, String field) {
        return deviceRepository.getPage(projectRepository.getById(project), ObjectUtil.convertToPageable(type, field, size, page));
    }

    private void checkStates(String state, List<String> states) {
        if (!states.contains(state)) {
            throw new BadRequestApplicationException("states", "States not contains state");
        }

        if (CollectionUtil.hasListDuplicates(states)) {
            throw new BadRequestApplicationException("states", "States has duplicate");
        }
    }
}
