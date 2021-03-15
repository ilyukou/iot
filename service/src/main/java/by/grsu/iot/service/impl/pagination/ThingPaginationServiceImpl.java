package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.domain.response.PaginationInfo;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.interf.pagination.ThingPaginationService;
import by.grsu.iot.service.util.CollectionUtil;
import by.grsu.iot.service.validation.access.interf.DeviceAccessValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThingPaginationServiceImpl implements ThingPaginationService {

    @Value("${project.thing.per-page}")
    private Long THING_PER_PAGE;

    private final ProjectRepository projectRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceAccessValidationService deviceAccessValidationService;

    public ThingPaginationServiceImpl(
            ProjectRepository projectRepository,
            DeviceRepository deviceRepository,
            DeviceAccessValidationService deviceAccessValidationService
    ) {
        this.projectRepository = projectRepository;
        this.deviceRepository = deviceRepository;
        this.deviceAccessValidationService = deviceAccessValidationService;
    }

    // in current version Device is a only IotThing
    @Override
    public List<? extends IotThing> getThingsFromProjectPage(Long projectId, Integer page, String username) {
        deviceAccessValidationService.checkPageReadAccess(projectId, username);

        if(!projectRepository.isExist(projectId)){
            throw new EntityNotFoundException("Project does not exist with given id={" + projectId + "}");
        }

        List<Long> deviceIds = deviceRepository.getProjectDeviceIds(projectId)
                .stream()
                .sorted()
                .collect(Collectors.toList());

        List<Long> requiredPageWithDeviceIds =
                CollectionUtil.getArrayFromTo((page - 1) * THING_PER_PAGE, page * THING_PER_PAGE, deviceIds);


        return deviceRepository.getByIds(requiredPageWithDeviceIds)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public PaginationInfo getPaginationInfo(Long projectId, String username) {
        deviceAccessValidationService.checkPaginationInfoReadAccess(projectId, username);

        if (!projectRepository.isExist(projectId)){
            throw new EntityNotFoundException("Not found project with such id={" + projectId + "}");
        }

        if (!projectRepository.getProjectOwnerUsername(projectId).equals(username)){
            throw new NotAccessForOperationException("That user not has project with such id");
        }

        Integer size = projectRepository.getProjectIotThingSize(projectId);

        return new PaginationInfo(
                CollectionUtil.getCountOfArrayPortion(size, THING_PER_PAGE),
                size,
                THING_PER_PAGE
        );
    }
}
