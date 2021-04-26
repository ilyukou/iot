package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.access.interf.pagination.ThingPaginationAccessService;
import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.model.dto.thing.ThingEnum;
import by.grsu.iot.model.dto.thing.ThingWrapper;
import by.grsu.iot.model.dto.thing.sensor.SensorValue;
import by.grsu.iot.model.service.PaginationIotThing;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.service.interf.pagination.ThingPaginationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ThingPaginationServiceImpl implements ThingPaginationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ThingPaginationServiceImpl.class);

    private static final String MODULE = "by.grsu.iot.service.";
    private static final String THING_PER_PAGE_PROPERTY = MODULE + "project.thing.per-page";
    private static final String SENSOR_VALUES_COUNT = MODULE + "sensor.values.count";
    private static Long THING_PER_PAGE;
    private static Integer COUNT_VALUES;

    private final ProjectRepository projectRepository;
    private final DeviceRepository deviceRepository;
    private final ThingPaginationAccessService thingPaginationAccessService;
    private final SensorRepository sensorRepository;
    private final SensorValueRepository sensorValueRepository;

    public ThingPaginationServiceImpl(
            Environment environment,
            ProjectRepository projectRepository,
            DeviceRepository deviceRepository,
            ThingPaginationAccessService thingPaginationAccessService,
            SensorRepository sensorRepository,
            SensorValueRepository sensorValueRepository) {
        this.projectRepository = projectRepository;
        this.deviceRepository = deviceRepository;
        this.thingPaginationAccessService = thingPaginationAccessService;
        this.sensorRepository = sensorRepository;

        THING_PER_PAGE = Long.valueOf(Objects.requireNonNull(environment.getProperty(THING_PER_PAGE_PROPERTY)));
        COUNT_VALUES = Integer.valueOf(Objects.requireNonNull(environment.getProperty(SENSOR_VALUES_COUNT)));
        this.sensorValueRepository = sensorValueRepository;
    }


    @Override
    public List<ThingWrapper> getThingsFromProjectPage(Long projectId, Integer page, String username) {
        thingPaginationAccessService.checkPageReadAccess(projectId, username);

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


        List<ThingWrapper> result = new ArrayList<>();

        for (PaginationIotThing thing : requiredPageWithDeviceIds) {
            if (thing.getType().equals(ThingEnum.device)) {
                result.add(new ThingWrapper(deviceRepository.getById(thing.getId())));

            } else if (thing.getType().equals(ThingEnum.sensor)) {
                // FIXME
                result.add(new ThingWrapper(sensorRepository.getById(thing.getId()),
                        sensorValueRepository.getLastValuePiece(sensorRepository.getTokenById(thing.getId()), COUNT_VALUES)
                                .stream()
                                .map(SensorValue::new)
                                .collect(Collectors.toList())));
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
        thingPaginationAccessService.checkPaginationInfoReadAccess(projectId, username);

        Integer size = projectRepository.getProjectIotThingSize(projectId);

        return new PaginationInfo(
                CollectionUtil.getCountOfArrayPortion(size, THING_PER_PAGE),
                size,
                THING_PER_PAGE
        );
    }
}
