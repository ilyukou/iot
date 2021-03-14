package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;

import java.util.List;
import java.util.Set;

/**
 * Repository for CRUD operation with {@link Project}
 */
public interface ProjectRepository {

    /**
     * Create {@link Project}
     * @param name {@link Project#getName()}
     * @param username {@link User#getUsername()}
     * @param title {@link Project#getTitle()}
     * @return created {@link Project}
     */
    Project create(final String name, final String username, final String title);

    /**
     * Update {@link Project}
     * @param project {@link Project}
     * @return updated {@link Project}
     */
    Project update(final Project project);

    /**
     * Get {@link Project} by {@link Project#getId()}
     * @param id {@link Project#getId()}
     * @return {@link Project} or {@code null}
     */
    Project getById(final Long id);

    /**
     * Not recommended use {@link User#getProjects()} because {@link User#getProjects()}
     * has {@code FetchType.LAZY}
     *
     * @param user {@link Project#getUser()}
     * @return {@link Set<Project>} or empty {@link Set}
     */
    Set<Project> getUserProjectsByUser(final User user);

    /**
     * Is exist {@link Project} by {@link Project#getId()}
     * @param id {@link Project#getId()}
     * @return {@code true} if exist or {@code false}
     */
    boolean isExist(final Long id);

    /**
     * Delete {@link Project} by {@link Project#getId()}
     *
     * @param id {@link Project#getId()}
     * @return {@code true} if deleted or {@code false}
     */
    boolean delete(final Long id);

    String getProjectOwnerUsername(Long project);

    List<Long> getUserPublicProjectIds(String username);

    List<Project> getByIds(List<Long> projectsId);

    List<Long> getAllUserProjectsIds(String username);

    Integer getUserPublicProjectSize(String username);

    Integer getAllUserProjectsSize(String username);

    Integer getProjectIotThingSize(Long projectId);
}
