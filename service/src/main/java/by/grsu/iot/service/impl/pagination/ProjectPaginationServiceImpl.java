package by.grsu.iot.service.impl.pagination;

import by.grsu.iot.model.dto.pagination.PaginationInfo;
import by.grsu.iot.model.exception.BadRequestApplicationException;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.interf.pagination.ProjectPaginationService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@PropertySource("classpath:application-service.properties")
@Service
public class ProjectPaginationServiceImpl implements ProjectPaginationService {

    private static final String PROJECT_PER_PAGE_PROPERTY = "by.grsu.iot.service.project.per-page";
    private static Long PROJECT_PER_PAGE;

    private final Environment environment;

    private final ProjectRepository projectRepository;

    public ProjectPaginationServiceImpl(
            Environment environment, ProjectRepository projectRepository) {
        this.environment = environment;
        this.projectRepository = projectRepository;

        PROJECT_PER_PAGE = Long.valueOf(Objects.requireNonNull(environment.getProperty(PROJECT_PER_PAGE_PROPERTY)));
    }


    @Override
    public List<Project> getProjectsFromPage(Integer page, String whoBeingAskedUsername, String whoRequestedUsername) {

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

        Integer projectsSize = projectRepository.getUserProjectsSize(whoBeingAskedUsername);

        return new PaginationInfo(
                CollectionUtil.getCountOfArrayPortion(projectsSize, PROJECT_PER_PAGE),
                projectsSize,
                PROJECT_PER_PAGE
        );
    }
}
