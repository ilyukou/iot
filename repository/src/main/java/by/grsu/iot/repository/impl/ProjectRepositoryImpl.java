package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.Status;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.ProjectJpaRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectRepositoryImpl.class);

    private final ProjectJpaRepository projectJpaRepository;
    private final UserRepository userRepository;
    private final TimeUtil timeUtil;

    public ProjectRepositoryImpl(ProjectJpaRepository projectJpaRepository, UserRepository userRepository, TimeUtil timeUtil) {
        this.projectJpaRepository = projectJpaRepository;
        this.userRepository = userRepository;
        this.timeUtil = timeUtil;
    }


    @Override
    public Project create(String name, String username, String title) {
        User user = userRepository.getByUsername(username);

        if (user == null) {
            String ms = "User must be not null";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        Set<Project> projects = user.getProjects();

        Project newProject = EntityFactory.createProject();
        newProject.setName(name);
        newProject.setTitle(title);
        newProject.setUser(user);

        projects.add(newProject);
        user.setProjects(projects);
        userRepository.update(user);

        return projectJpaRepository.save(newProject);
    }

    @Override
    public Project update(Project project) {

        if (project == null || project.getId() == null) {
            String ms = "Update project is null or project.id is null";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        project.setUpdated(timeUtil.getCurrentDate());

        return projectJpaRepository.save(project);
    }

    @Override
    public Project getById(Long id) {
        return projectJpaRepository.findById(id).orElse(null);
    }

    @Override
    public boolean disableProjectByProjectId(Long projectId) {
        Project project = getById(projectId);

        if (project == null) {
            return false;
        }

        project.setStatus(Status.DISABLED);

        update(project);

        return true;
    }

    @Override
    public boolean isExist(Long id) {
        return projectJpaRepository.existsById(id);
    }

    @Override
    public List<Long> getProjects(Long userId) {
        return projectJpaRepository.findProjectsIdByUserId(userId);
    }

    @Override
    public List<Project> getProjectsByIds(List<Long> ids) {
        return null;
    }

    @Override
    public boolean isUserProject(Long projectId, String username) {
        return false;
    }
}
