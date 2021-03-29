package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.domain.PaginationIotThing;
import by.grsu.iot.model.dto.thing.ThingEnum;
import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.service.interf.pagination.ThingPaginationService;
import by.grsu.iot.service.validation.access.interf.DeviceAccessValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThingPaginationServiceImpl implements ThingPaginationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThingPaginationServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceAccessValidationService deviceAccessValidationService;
    private final SensorRepository sensorRepository;

    @Value("${project.thing.per-page}")
    private Long THING_PER_PAGE;

    public ThingPaginationServiceImpl(
            ProjectRepository projectRepository,
            DeviceRepository deviceRepository,
            DeviceAccessValidationService deviceAccessValidationService,
            SensorRepository sensorRepository) {
        this.projectRepository = projectRepository;
        this.deviceRepository = deviceRepository;
        this.deviceAccessValidationService = deviceAccessValidationService;
        this.sensorRepository = sensorRepository;
    }

    // in current version Device is a only IotThing
    @Override
    public List<? extends IotThing> getThingsFromProjectPage(Long projectId, Integer page, String username) {
        deviceAccessValidationService.checkPageReadAccess(projectId, username);

        List<PaginationIotThing> things = deviceRepository.getProjectDeviceIds(projectId)
                .stream()
                .sorted()
                .map(id -> new PaginationIotThing(id, ThingEnum.device))
                .collect(Collectors.toList());

        things.addAll(sensorRepository.getProjectSensorIds(projectId)
                .stream()
                .sorted()
                .map(id -> new PaginationIotThing(id, ThingEnum.sensor))
                .collect(Collectors.toList()));

        List<PaginationIotThing> requiredPageWithDeviceIds =
                CollectionUtil.getArrayFromTo((page - 1) * THING_PER_PAGE, page * THING_PER_PAGE, things);


        List<IotThing> result = new ArrayList<>();

        for (PaginationIotThing thing : requiredPageWithDeviceIds){
            if (thing.getType().equals(ThingEnum.device)){
                result.add(deviceRepository.getById(thing.getId()));

            } else if(thing.getType().equals(ThingEnum.sensor)){
                result.add(sensorRepository.getById(thing.getId()));

            } else {
                LOGGER.warn("Not found a such ThingEnum");
            }
        }

        return result
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public PaginationInfo getPaginationInfo(Long projectId, String username) {
        deviceAccessValidationService.checkPaginationInfoReadAccess(projectId, username);

        Integer size = projectRepository.getProjectIotThingSize(projectId);

        return new PaginationInfo(
                CollectionUtil.getCountOfArrayPortion(size, THING_PER_PAGE),
                size,
                THING_PER_PAGE
        );
    }
}
