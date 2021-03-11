package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.domain.form.ProjectForm;
import by.grsu.iot.service.domain.form.ProjectFormUpdate;

public interface ProjectCrudService {

    Project create(ProjectForm projectForm, String username);

    Project getById(Long id, String username);

    Project update(Long id, ProjectFormUpdate projectFormUpdate, String username);

    boolean deleteById(Long id, String username);

    Project getById(Long projectId);
}
