package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.domain.response.PaginationInfo;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.interf.pagination.ProjectPaginationService;
import by.grsu.iot.service.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:application-service.properties")
@Service
public class ProjectPaginationServiceImpl implements ProjectPaginationService {

    @Value("${project.per-page}")
    private Long PROJECT_PER_PAGE;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public ProjectPaginationServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getProjectsFromPage(Integer page, String whoBeingAskedUsername, String whoRequestedUsername) {

        if(!userRepository.isExistByUsername(whoBeingAskedUsername)){
            throw new EntityNotFoundException("User does not exist with given username={" + whoBeingAskedUsername + "}");
        }

        List<Long> projectsId;

        if (!whoBeingAskedUsername.equals(whoRequestedUsername)){
            projectsId = projectRepository.getUserPublicProjectIds(whoBeingAskedUsername);
        } else {
            projectsId = projectRepository.getAllUserProjectsIds(whoBeingAskedUsername);
        }

        projectsId = projectsId.stream().sorted().collect(Collectors.toList());

        List<Long> requiredPageWithProjectIds =
                CollectionUtil.getArrayFromTo((page - 1) * PROJECT_PER_PAGE, page * PROJECT_PER_PAGE, projectsId);

        return projectRepository.getByIds(requiredPageWithProjectIds)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public PaginationInfo getPaginationInfo(String whoBeingAskedUsername, String whoRequestedUsername) {

        Integer projectsSize;

        if (!whoBeingAskedUsername.equals(whoRequestedUsername)){
            projectsSize = projectRepository.getUserPublicProjectSize(whoBeingAskedUsername);
        } else {
            projectsSize = projectRepository.getAllUserProjectsSize(whoBeingAskedUsername);
        }

        return new PaginationInfo(
                CollectionUtil.getCountOfArrayPortion(projectsSize, PROJECT_PER_PAGE),
                projectsSize,
                PROJECT_PER_PAGE
        );
    }
}
