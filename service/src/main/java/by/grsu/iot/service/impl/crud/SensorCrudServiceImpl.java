package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.dto.PageWrapper;
import by.grsu.iot.model.dto.sort.RequestSortType;
import by.grsu.iot.model.dto.thing.sensor.SensorDto;
import by.grsu.iot.model.dto.thing.sensor.SensorForm;
import by.grsu.iot.model.dto.thing.sensor.SensorFormUpdate;
import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.model.sql.Sensor;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.interf.crud.SensorCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
public class SensorCrudServiceImpl implements SensorCrudService {

    private final Integer DEFAULT_SIZE = 10;

    private final SensorRepository sensorRepository;
    private final ProjectCrudService projectCrudService;
    private final SensorValueRepository sensorValueRepository;

    public SensorCrudServiceImpl(SensorRepository sensorRepository, ProjectCrudService projectCrudService, @Lazy SensorValueRepository sensorValueRepository) {
        this.sensorRepository = sensorRepository;
        this.projectCrudService = projectCrudService;
        this.sensorValueRepository = sensorValueRepository;
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

    @Override
    public PageWrapper<SensorDto> getPage(Long project, Integer size, Integer page, RequestSortType type, String field) {
        Page<Sensor> p = sensorRepository.getPage(projectCrudService.getById(project),
                ObjectUtil.convertToPageable(type, field, size, page));

        Map<String, List<SensorValueElasticsearch>> map = sensorValueRepository
                .getSensorLastValuesByTokens(p.getContent()
                        .stream()
                        .map(Sensor::getToken)
                        .collect(Collectors.toList()), DEFAULT_SIZE);

        return new PageWrapper<>(
                p.getContent()
                        .stream()
                        .map(sensor -> new SensorDto(sensor, ObjectUtil.convertToSensorValue(map.get(sensor.getToken()))))
                        .collect(Collectors.toList()),
                p.hasNext(), p.getTotalPages(), p.getTotalElements());
    }
}
