package by.grsu.iot.service.impl.crud;

import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.service.interf.crud.ProjectCrudService;
import by.grsu.iot.service.util.ObjectUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProjectCrudServiceImpl implements ProjectCrudService {

    private final ProjectRepository projectRepository;

    public ProjectCrudServiceImpl(
            ProjectRepository projectRepository
    ) {
        this.projectRepository = projectRepository;
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
}
