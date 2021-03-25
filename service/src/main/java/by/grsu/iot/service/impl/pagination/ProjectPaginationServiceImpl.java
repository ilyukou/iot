package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.domain.pagination.PaginationInfo;
import by.grsu.iot.service.exception.BadRequestApplicationException;
import by.grsu.iot.service.interf.pagination.ProjectPaginationService;
import by.grsu.iot.service.validation.access.interf.ProjectAccessValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:application-service.properties")
@Service
public class ProjectPaginationServiceImpl implements ProjectPaginationService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectAccessValidationService projectAccessValidationService;
    @Value("${project.per-page}")
    private Long PROJECT_PER_PAGE;

    public ProjectPaginationServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            ProjectAccessValidationService projectAccessValidationService
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectAccessValidationService = projectAccessValidationService;
    }

    @Override
    public List<Project> getProjectsFromPage(Integer page, String whoBeingAskedUsername, String whoRequestedUsername) {
        projectAccessValidationService.checkPageReadAccess(whoBeingAskedUsername, whoRequestedUsername);

        PaginationInfo info = getPaginationInfo(whoBeingAskedUsername, whoRequestedUsername);

        if (page > info.getPages()) {
            throw new BadRequestApplicationException("page", "You are requesting a non-existent page");
        }

        List<Long> projectsId = projectRepository.getUserProjectsIds(whoBeingAskedUsername)
                .stream()
                .sorted()
                .collect(Collectors.toList());

        List<Long> requiredPageWithProjectIds =
                CollectionUtil.getArrayFromTo((page - 1) * PROJECT_PER_PAGE, page * PROJECT_PER_PAGE, projectsId);

        return projectRepository.getByIds(requiredPageWithProjectIds)
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public PaginationInfo getPaginationInfo(String whoBeingAskedUsername, String whoRequestedUsername) {

        projectAccessValidationService.checkPaginationInfoReadAccess(whoBeingAskedUsername, whoRequestedUsername);

        Integer projectsSize = projectRepository.getUserProjectsSize(whoBeingAskedUsername);

        return new PaginationInfo(
                CollectionUtil.getCountOfArrayPortion(projectsSize, PROJECT_PER_PAGE),
                projectsSize,
                PROJECT_PER_PAGE
        );
    }
}
