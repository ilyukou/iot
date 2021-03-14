package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.service.domain.request.project.ProjectForm;
import by.grsu.iot.service.domain.request.project.ProjectFormUpdate;
/**
 * Service layer for CRUD operation with {@link Project}
 */
public interface ProjectCrudService {

    /**
     * Create {@link Project}
     * @param projectForm form with fields
     * @param username who request a create project
     * @return created {@link Project}
     */
    Project create(ProjectForm projectForm, String username);

    /**
     * Get {@link Project} by {@link Project#getId()}
     * @param id {@link Project#getId()}
     * @param username who request a get project
     * @return {@link Project}
     */
    Project getById(Long id, String username);

    /**
     * Update {@link Project}
     * @param id {@link Project#getId()}
     * @param projectFormUpdate form with updated fields
     * @param username who request a update project
     * @return updated {@link Project}
     */
    Project update(Long id, ProjectFormUpdate projectFormUpdate, String username);

    /**
     * Delete {@link Project}
     * @param id {@link Project}
     * @param username who request a delete project
     */
    void delete(Long id, String username);
}
