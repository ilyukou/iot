package by.grsu.iot.service.impl.crud;

import by.grsu.iot.access.interf.crud.ProjectAccessService;
import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.dto.sort.RequestSortType;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.interf.crud.UserCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProjectCrudServiceImpl implements ProjectCrudService {

    private final ProjectRepository projectRepository;
    private final ProjectAccessService projectAccessService;
    private final UserCrudService userCrudService;

    public ProjectCrudServiceImpl(
            ProjectRepository projectRepository,
            ProjectAccessService projectAccessService,
            UserCrudService userCrudService
    ) {
        this.projectRepository = projectRepository;
        this.projectAccessService = projectAccessService;
        this.userCrudService = userCrudService;
    }

    @Override
    public Project create(ProjectForm projectForm, String username) {
        projectAccessService.checkCreateAccess(username);

        return projectRepository.create(projectForm.getName(), username, projectForm.getTitle());
    }

    @Override
    public Project update(Long id, ProjectFormUpdate projectFormUpdate, String username) {
        projectAccessService.checkUpdateAccess(username, id);

        Project project = ObjectUtil.updateField(getById(id, username), projectFormUpdate);

        return projectRepository.update(project);
    }

    @Override
    public Project getById(Long id, String username) {
        projectAccessService.checkReadAccess(username, id);

        return projectRepository.getById(id);
    }

    @Override
    public void delete(Long id, String username) {
        projectAccessService.checkDeleteAccess(username, id);

        projectRepository.delete(id);
    }

    @Override
    public Page<Project> getPage(String username, Integer size, Integer page, RequestSortType type, String field) {
        return projectRepository.getPage(userCrudService.getByUsername(username), ObjectUtil.convertToPageable(type, field, size, page));
    }
}
