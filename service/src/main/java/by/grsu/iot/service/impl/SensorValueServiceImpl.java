package by.grsu.iot.service.impl;

import by.grsu.iot.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.model.exception.BadRequestApplicationException;
import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.service.interf.SensorValueService;
import by.grsu.iot.service.interf.crud.SensorCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class SensorValueServiceImpl implements SensorValueService {

    private final SensorValueRepository sensorValueRepository;
    private final SensorCrudService sensorCrudService;

    public SensorValueServiceImpl(
            SensorValueRepository sensorValueRepository,
            SensorCrudService sensorCrudService
    ) {
        this.sensorValueRepository = sensorValueRepository;
        this.sensorCrudService = sensorCrudService;
    }

    @Override
    public void add(String token, SensorValue form) {
        sensorValueRepository.add(token, form.getTime(), form.getValue());
    }

    @Override
    public List<SensorValue> get(String token, Long from, Long to) {
        if (!sensorCrudService.isExist(token)){
            throw new BadRequestApplicationException("token", "Not found sensor with such token");
        }


        return sensorValueRepository.get(token, from, to).stream()
                .map(SensorValue::new)
                .collect(Collectors.toList());
    }

    @Override
    public SensorValue getOneValue(String token) {
        SensorValueElasticsearch sensorValue = sensorValueRepository.get(token);

        if (sensorValue == null){
            throw new EntityNotFoundApplicationException("Not found SensorValue for such sensor");
        }

        return new SensorValue(sensorValue);
    }
}
