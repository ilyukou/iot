package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.domain.PaginationInfo;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.interf.crud.UserCrudService;
import by.grsu.iot.service.interf.pagination.ThingPaginationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThingPaginationServiceImpl implements ThingPaginationService {

    @Value("${device.per-page}")
    private Long DEVICE_PER_PAGE;

    @Value("${project.per-page}")
    private Long PROJECT_PER_PAGE;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectCrudService projectCrudService;
    private final UserCrudService userCrudService;

    public ThingPaginationServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            ProjectCrudService projectCrudService,
            UserCrudService userCrudService
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectCrudService = projectCrudService;
        this.userCrudService = userCrudService;
    }

    public Integer getCountThingPages(Long projectId, String username) {
        Project project = projectRepository.getById(projectId);

        if (project == null){
            throw new EntityNotFoundException("Not found project with such id={" + projectId + "}");
        }

        if (!project.getUser().getUsername().equals(username)){
            throw new NotAccessForOperationException("That user not has project with such id");
        }

        return getCountOfPage(project.getDevices().size(), DEVICE_PER_PAGE );
    }

    @Override
    public List<? extends IotThing> getThingsFromProjectPage(Long projectId, Integer count, String username) {
        Project project = projectRepository.getById(projectId);

        if(project == null){
            throw new EntityNotFoundException("Project does not exist with given id={" + projectId + "}");
        }

        List<? extends IotThing> projects = project.getDevices().stream().sorted().collect(Collectors.toList());

        return getIotThingFromTo((count - 1) * PROJECT_PER_PAGE, count * PROJECT_PER_PAGE, projects);
    }

    @Override
    public PaginationInfo getPaginationInfoA(Long projectId, String username) {
        return new PaginationInfo(
                getCountThingPages(projectId, username),

                // FIXME - add method for getting iotThing.size
                projectCrudService.getById(projectId).getDevices().size(),
                DEVICE_PER_PAGE
        );
    }

    private List<? extends IotThing> getIotThingFromTo(Long from, Long to, List<? extends IotThing> devices){

        if(from > devices.size()){
            throw new BadRequestException("count", "Not exist such page");
        }

        if(to > devices.size()){
            to = (long) devices.size();
        }

        return devices.subList(from.intValue(), to.intValue());
    }

    private Integer getCountOfPage(Integer arraySize, Long elementPerPage){
        if(arraySize == 0){
            return 0;
        }

        if(arraySize <= elementPerPage){
            return 1;
        }

        if(arraySize % elementPerPage == 0){
            return Math.toIntExact(arraySize / elementPerPage);
        }

        int c = Math.toIntExact(arraySize % elementPerPage);

        return Math.toIntExact((arraySize - c) / elementPerPage) + 1;
    }
}
