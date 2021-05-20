package by.grsu.iot.service.interf.crud;

import by.grsu.iot.model.dto.project.ProjectForm;
import by.grsu.iot.model.dto.project.ProjectFormUpdate;
import by.grsu.iot.model.sql.Project;

/**
 * Service layer for CRUD operation with {@link Project}
 *
 * @author Ilyukou Ilya
 */
public interface ProjectCrudService {

    /**
     * Create {@link Project}
     *
     * @param projectForm form with fields
     * @param username    who request a create project
     * @return created {@link Project}
     */
    Project create(ProjectForm projectForm, String username);

    /**
     * Get {@link Project} by {@link Project#getId()}
     *
     * @param id {@link Project#getId()}
     * @return {@link Project}
     */
    Project getById(Long id);

    /**
     * Update {@link Project}
     *
     * @param id                {@link Project#getId()}
     * @param projectFormUpdate form with updated fields
     * @return updated {@link Project}
     */
    Project update(Long id, ProjectFormUpdate projectFormUpdate);

    /**
     * Delete {@link Project}
     *
     * @param id       {@link Project}
     */
    void delete(Long id);
}
