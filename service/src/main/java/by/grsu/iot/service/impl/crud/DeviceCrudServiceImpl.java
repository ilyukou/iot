package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.dto.thing.device.DeviceForm;
import by.grsu.iot.model.dto.thing.device.DeviceFormUpdate;
import by.grsu.iot.model.exception.BadRequestApplicationException;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.access.interf.crud.DeviceAccessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DeviceCrudServiceImpl implements DeviceCrudService {

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;
    private final DeviceAccessService deviceAccessService;

    public DeviceCrudServiceImpl(
            DeviceRepository deviceRepository,
            ProjectRepository projectRepository,
            DeviceAccessService deviceAccessService) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
        this.deviceAccessService = deviceAccessService;
    }


    @Override
    public Device create(DeviceForm deviceForm, String username) {
        deviceAccessService.checkCreateAccess(username, deviceForm.getProject());

        checkStates(deviceForm.getState(), deviceForm.getStates());

        return deviceRepository.create(projectRepository.getById(deviceForm.getProject()), deviceForm.convert());
    }

    @Override
    public Device getById(Long id, String username) {
        deviceAccessService.checkReadAccess(username, id);

        return deviceRepository.getById(id);
    }

    @Override
    public void delete(Long id, String username) {
        deviceAccessService.checkDeleteAccess(username, id);

        deviceRepository.delete(id);
    }

    @Override
    public Device update(Long id, DeviceFormUpdate deviceFormUpdate, String username) {
        deviceAccessService.checkUpdateAccess(username, id);

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

    private void checkStates(String state, List<String> states) {
        if (!states.contains(state)) {
            throw new BadRequestApplicationException("states", "States not contains state");
        }

        if (CollectionUtil.hasListDuplicates(states)) {
            throw new BadRequestApplicationException("states", "States has duplicate");
        }
    }
}
