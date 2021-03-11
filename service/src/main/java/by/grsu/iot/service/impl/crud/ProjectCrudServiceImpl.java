package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.domain.form.ProjectForm;
import by.grsu.iot.service.domain.form.ProjectFormUpdate;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.interf.crud.UserCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import by.grsu.iot.service.validation.ProjectValidation;
import org.springframework.stereotype.Service;

@Service
public class ProjectCrudServiceImpl implements ProjectCrudService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserCrudService userCrudService;
    private final ProjectValidation projectValidation;

    public ProjectCrudServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            UserCrudService userCrudService,
            ProjectValidation projectValidation
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.userCrudService = userCrudService;
        this.projectValidation = projectValidation;
    }

    @Override
    public Project create(ProjectForm projectForm, String username) {
        projectValidation.validateCreateProject(username, projectForm);

        return projectRepository.create(projectForm.getName(), username, projectForm.getTitle());
    }

    @Override
    public Project update(Long id, ProjectFormUpdate projectFormUpdate, String username) {
        projectValidation.validateUpdateProject(username, id, projectFormUpdate);

        Project project = ObjectUtil.updateField(getById(id, username), projectFormUpdate);

        return projectRepository.update(project);
    }

    @Override
    public Project getById(Long id, String username) {
        projectValidation.validateReadProject(username, id);

        return projectRepository.getById(id);
    }

    @Override
    public boolean deleteById(Long id, String username) {
        projectValidation.validateDeleteProject(username, id);

        return projectRepository.delete(id);
    }

    @Override
    public Project getById(Long projectId) {
        return projectRepository.getById(projectId);
    }
}
