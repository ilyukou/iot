package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.access.interf.crud.ProjectAccessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProjectCrudServiceImpl implements ProjectCrudService {

    private final ProjectRepository projectRepository;
    private final ProjectAccessService projectAccessService;

    public ProjectCrudServiceImpl(
            ProjectRepository projectRepository,
            ProjectAccessService projectAccessService
    ) {
        this.projectRepository = projectRepository;
        this.projectAccessService = projectAccessService;
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
}
