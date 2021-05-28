package by.grsu.iot.api.service;

import by.grsu.iot.api.model.annotation.Logging;
import by.grsu.iot.api.model.annotation.Profiling;
import by.grsu.iot.api.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.api.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.api.model.exception.BadRequestApplicationException;
import by.grsu.iot.api.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.api.repository.sql.project.thing.SensorValueRepository;
import by.grsu.iot.api.service.crud.project.thing.SensorCrudService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Logging
@Profiling
@Transactional
@Service
public class SensorValueServiceImpl implements SensorValueService {

    @Value("${by.grsu.iot.service.sensor.value.piece.size}")
    private Integer SENSOR_VALUE_PIECE_SIZE_PROPERTY;

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
    public List<SensorValue> get(String token, Long from, Long to) throws IOException {
        if (!sensorCrudService.isExist(token)) {
            throw new BadRequestApplicationException("token", "Not found sensor with such token");
        }

        return sensorValueRepository.get(token, from, to, SENSOR_VALUE_PIECE_SIZE_PROPERTY).stream()
                .map(SensorValue::new)
                .collect(Collectors.toList());
    }

    @Override
    public SensorValue getOneValue(String token) throws IOException {
        SensorValueElasticsearch sensorValue = sensorValueRepository.get(token);

        if (sensorValue == null) {
            throw new EntityNotFoundApplicationException("Not found SensorValue for such sensor");
        }

        return new SensorValue(sensorValue);
    }
}
