package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.dto.thing.sensor.SensorForm;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.service.interf.crud.SensorCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.service.validation.access.interf.SensorAccessValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SensorCrudServiceImpl implements SensorCrudService {

    private final SensorRepository sensorRepository;
    private final SensorAccessValidationService sensorAccessValidationService;

    public SensorCrudServiceImpl(SensorRepository sensorRepository, SensorAccessValidationService sensorAccessValidationService) {
        this.sensorRepository = sensorRepository;
        this.sensorAccessValidationService = sensorAccessValidationService;
    }

    @Override
    public Sensor create(SensorForm sensorForm, String username) {
        sensorAccessValidationService.checkCreateAccess(username, sensorForm.getProject());

        return sensorRepository.create(sensorForm.getProject(), sensorForm.getName());
    }

    @Override
    public Sensor update(Long id, SensorFormUpdate sensorFormUpdate, String username) {
        sensorAccessValidationService.checkUpdateAccess(username, id);

        Sensor sensor = getById(id, username);

        sensor = ObjectUtil.updateField(sensor, sensorFormUpdate);

        return sensorRepository.update(sensor);
    }

    @Override
    public Sensor getById(Long id, String username) {
        sensorAccessValidationService.checkReadAccess(username, id);

        return sensorRepository.getById(id);
    }

    @Override
    public void delete(Long id, String username) {
        sensorAccessValidationService.checkDeleteAccess(username, id);

        sensorRepository.delete(id);
    }

    @Override
    public boolean isExist(String token) {
        return sensorRepository.isExist(token);
    }
}
