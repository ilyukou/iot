package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.dto.thing.sensor.SensorForm;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.service.interf.crud.SensorCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SensorCrudServiceImpl implements SensorCrudService {

    private final SensorRepository sensorRepository;

    public SensorCrudServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Sensor create(SensorForm sensorForm) {
        return sensorRepository.create(sensorForm.getProject(), sensorForm.getName());
    }

    @Override
    public Sensor update(Long id, SensorFormUpdate sensorFormUpdate) {
        Sensor sensor = getById(id);

        sensor = ObjectUtil.updateField(sensor, sensorFormUpdate);

        return sensorRepository.update(sensor);
    }

    @Override
    public Sensor getById(Long id) {
        return sensorRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        sensorRepository.delete(id);
    }

    @Override
    public boolean isExist(String token) {
        return sensorRepository.isExist(token);
    }
}
