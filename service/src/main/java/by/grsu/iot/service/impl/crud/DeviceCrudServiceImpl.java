package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.domain.request.device.DeviceForm;
import by.grsu.iot.service.domain.request.device.DeviceFormUpdate;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.service.validation.access.interf.DeviceAccessValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeviceCrudServiceImpl implements DeviceCrudService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceCrudServiceImpl.class);

    private final DeviceRepository deviceRepository;
    private final ProjectRepository projectRepository;
    private final DeviceAccessValidationService deviceAccessValidationService;

    public DeviceCrudServiceImpl(
            DeviceRepository deviceRepository,
            ProjectRepository projectRepository,
            DeviceAccessValidationService deviceAccessValidationService) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
        this.deviceAccessValidationService = deviceAccessValidationService;
    }


    @Override
    public Device create(DeviceForm deviceForm, String username) {
        deviceAccessValidationService.checkCreateAccess(username, deviceForm.getProject());

        if (!deviceForm.getStates().contains(deviceForm.getState())){
            throw new BadRequestException("states", "States not contains state");
        }

        return deviceRepository.create(projectRepository.getById(deviceForm.getProject()), deviceForm.convert());
    }

    @Override
    public Device getById(Long id, String username) {
        deviceAccessValidationService.checkReadAccess(username, id);

        return deviceRepository.getById(id);
    }

    @Override
    public void delete(Long id, String username) {
        deviceAccessValidationService.checkDeleteAccess(username, id);

        deviceRepository.delete(id);
    }

    @Override
    public Device update(Long id, DeviceFormUpdate deviceFormUpdate, String username) {
        deviceAccessValidationService.checkUpdateAccess(username, id);

        Device device = deviceRepository.getById(id);

        if (!deviceFormUpdate.getStates().contains(device.getState())){
            throw new BadRequestException("states", "States not contains state");
        }

        device = ObjectUtil.updateField(deviceRepository.getById(id), deviceFormUpdate);

        return deviceRepository.update(device);
    }

    @Override
    public Device getByToken(String token) {
        Device device =  deviceRepository.getByToken(token);

        if (device == null){
            throw new EntityNotFoundException("Not found Device with such token");
        }

        return device;
    }
}
