package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Resource;
import by.grsu.iot.model.sql.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Repository for CRUD operation with {@link Project}
 *
 * @author Ilyukou Ilya
 */
public interface ProjectRepository {

    /**
     * Create {@link Project}
     *
     * @param name     {@link Project#getName()}
     * @param username {@link User#getUsername()}
     * @param title    {@link Project#getTitle()}
     * @return created {@link Project}
     */
    Project create(final String name, final String username, final String title);

    /**
     * Update {@link Project}
     *
     * @param project {@link Project}
     * @return updated {@link Project}
     */
    Project update(final Project project);

    /**
     * Get {@link Project} by {@link Project#getId()}
     *
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
     *
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

    /**
     * Get project owner {@link User#getUsername()}
     *
     * @param project {@link User#getUsername()}
     * @return {@link User#getUsername()} or null
     */
    String getProjectOwnerUsername(Long project);

    /**
     * Get {@link List} of {@link Project} by {@link Project#getId()}
     *
     * @param projectsId {@link List} of {@link Project#getId()}
     * @return {@link List} of {@link Project}
     */
    List<Project> getByIds(List<Long> projectsId);

    /**
     * Get {@link List} of {@link Project#getId()} by {@link User#getUsername()}
     *
     * @param username {@link User#getUsername()}
     * @return {@link List} of {@link Project#getId()}
     */
    List<Long> getUserProjectsIds(String username);

    /**
     * Get size of {@link User} projects
     *
     * @param username {@link User#getUsername()}
     * @return size of {@link User} projects
     */
    Integer getUserProjectsSize(String username);

    /**
     * Get size of {@link IotThing} in {@link Project} by {@link Project#getId()}
     *
     * @param projectId {@link Project#getId()}
     * @return size of {@link IotThing} in {@link Project}
     */
    Integer getProjectIotThingSize(Long projectId);

    /**
     * Get owner {@link User#getUsername()} by {@link Resource#getId()}
     *
     * @param resourceId {@link Resource#getId()}
     * @return {@link User#getUsername()}
     */
    String getProjectOwnerUsernameByResourceId(Long resourceId);

    /**
     * Get {@link Page} of {@link Project}
     *
     * @param user     {@link Project#getUser()}
     * @param pageable {@link Pageable}
     * @return page of projects
     */
    Page<Project> getPage(User user, Pageable pageable);

    boolean hasUserOwnerProject(String username, Long projectId);
}
