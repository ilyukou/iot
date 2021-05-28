package by.grsu.iot.api.service.crud.project;

import by.grsu.iot.api.model.annotation.Logging;
import by.grsu.iot.api.model.annotation.Profiling;
import by.grsu.iot.api.model.dto.project.ProjectForm;
import by.grsu.iot.api.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.api.model.dto.sort.RequestSortType;
import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.repository.sql.project.ProjectRepository;
import by.grsu.iot.api.service.crud.user.UserCrudService;
import by.grsu.iot.api.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Logging
@Profiling
@Transactional
@Service
public class ProjectCrudServiceImpl implements ProjectCrudService {

    private final ProjectRepository projectRepository;
    private final UserCrudService userCrudService;

    public ProjectCrudServiceImpl(
            ProjectRepository projectRepository,
            UserCrudService userCrudService
    ) {
        this.projectRepository = projectRepository;
        this.userCrudService = userCrudService;
    }

    @Override
    public Project create(ProjectForm projectForm, String username) {

        return projectRepository.create(projectForm.getName(), username, projectForm.getTitle());
    }

    @Override
    public Project update(Long id, ProjectFormUpdate projectFormUpdate) {
        Project project = ObjectUtil.updateField(getById(id), projectFormUpdate);

        return projectRepository.update(project);
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        projectRepository.delete(id);
    }

    @Override
    public Page<Project> getPage(String username, Integer size, Integer page, RequestSortType type, String field) {
        return projectRepository.getPage(userCrudService.getByUsername(username), ObjectUtil.convertToPageable(type, field, size, page));
    }
}
