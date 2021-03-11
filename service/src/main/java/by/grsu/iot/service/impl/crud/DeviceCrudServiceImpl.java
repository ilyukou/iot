package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.Device;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.domain.form.DeviceForm;
import by.grsu.iot.service.domain.form.DeviceFormUpdate;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.interf.crud.DeviceCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.service.validation.DeviceValidation;
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
    private final DeviceValidation deviceValidation;

    public DeviceCrudServiceImpl(
            DeviceRepository deviceRepository,
            ProjectRepository projectRepository,
            DeviceValidation deviceValidation
    ) {
        this.deviceRepository = deviceRepository;
        this.projectRepository = projectRepository;
        this.deviceValidation = deviceValidation;
    }


    @Override
    public Device create(DeviceForm deviceForm, String username) {
        deviceValidation.validateCreateDevice(username, deviceForm.getProject(), deviceForm);

        return deviceRepository.create(projectRepository.getById(deviceForm.getProject()), deviceForm.convert());
    }

    @Override
    public Device getById(Long id, String username) {
        deviceValidation.validateReadDevice(username, id);

        return deviceRepository.getById(id);
    }

    @Override
    public void deleteById(Long id, String username) {
        deviceValidation.validateDeleteDevice(username, id);

        deviceRepository.delete(id);
    }

    @Override
    public Device update(Long id, DeviceFormUpdate deviceFormUpdate, String username) {
        deviceValidation.validateUpdateDevice(username, id, deviceFormUpdate);

        Device device = ObjectUtil.updateField(deviceRepository.getById(id), deviceFormUpdate);

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
