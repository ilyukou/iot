package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.service.validation.access.interf.ProjectAccessValidationService;
import org.springframework.stereotype.Service;

@Service
public class ProjectCrudServiceImpl implements ProjectCrudService {

    private final ProjectRepository projectRepository;
    private final ProjectAccessValidationService projectAccessValidationService;

    public ProjectCrudServiceImpl(
            ProjectRepository projectRepository,
            ProjectAccessValidationService projectAccessValidationService
    ) {
        this.projectRepository = projectRepository;
        this.projectAccessValidationService = projectAccessValidationService;
    }

    @Override
    public Project create(ProjectForm projectForm, String username) {
        projectAccessValidationService.checkCreateAccess(username);

        return projectRepository.create(projectForm.getName(), username, projectForm.getTitle());
    }

    @Override
    public Project update(Long id, ProjectFormUpdate projectFormUpdate, String username) {
        projectAccessValidationService.checkUpdateAccess(username, id);

        Project project = ObjectUtil.updateField(getById(id, username), projectFormUpdate);

        return projectRepository.update(project);
    }

    @Override
    public Project getById(Long id, String username) {
        projectAccessValidationService.checkReadAccess(username, id);

        return projectRepository.getById(id);
    }

    @Override
    public void delete(Long id, String username) {
        projectAccessValidationService.checkDeleteAccess(username, id);

        projectRepository.delete(id);
    }
}
