package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.sql.AccessType;
import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.domain.PaginationInfo;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.interf.crud.UserCrudService;
import by.grsu.iot.service.interf.pagination.ProjectPaginationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PropertySource("classpath:application-service.properties")
@Service
public class ProjectPaginationServiceImpl implements ProjectPaginationService {

    @Value("${project.per-page}")
    private Long PROJECT_PER_PAGE;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserCrudService userCrudService;

    public ProjectPaginationServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            UserCrudService userCrudService
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.userCrudService = userCrudService;
    }

    @Override
    public List<Project> getProjectsFromPage(Integer count, String username) {
        User user = userRepository.getByUsername(username);

        if(user == null){
            throw new EntityNotFoundException("User does not exist with given username={" + username + "}");
        }

        if(user.getProjects().size() == 0){
            return new ArrayList<>();
        };

        List<Project> projects = user.getProjects().stream().sorted().collect(Collectors.toList());

        return getProjectFromTo((count - 1) * PROJECT_PER_PAGE, count * PROJECT_PER_PAGE, projects);
    }

    @Override
    public PaginationInfo getPaginationInfo(String requestedUsername, String usernameRequestingThis) {
        return new PaginationInfo(
                getCountOfProjectPage(requestedUsername, usernameRequestingThis),
                userCrudService.getByUsername(requestedUsername).getProjects().size(),
                PROJECT_PER_PAGE
        );
    }

    private List<Project> getProjectFromTo(Long from, Long to, List<Project> projects){

        if(from > projects.size()){
            throw new BadRequestException("count", "Not exist such page");
        }

        if(to > projects.size()){
            to = (long) projects.size();
        }

        return projects.subList(from.intValue(), to.intValue());
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

    public Integer getCountOfProjectPage(String requestedUsername, String usernameRequestingThis) {
        Set<Project> projectSet;

        if(requestedUsername == null){
            requestedUsername = usernameRequestingThis;
        }

        if(requestedUsername.equals(usernameRequestingThis)){
            projectSet = userRepository.getByUsername(requestedUsername).getProjects();
        } else {
            projectSet = userRepository.getByUsername(requestedUsername).getProjects().stream()
                    .filter(project -> project.getAccessType().equals(AccessType.PUBLIC))
                    .collect(Collectors.toSet());
        }

        return getCountOfPage(projectSet.size(), PROJECT_PER_PAGE );
    }
}
