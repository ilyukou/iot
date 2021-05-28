package by.grsu.iot.api.repository.sql.project;

import by.grsu.iot.api.model.sql.Project;
import by.grsu.iot.api.model.sql.User;
import by.grsu.iot.api.repository.EntityFactory;
import by.grsu.iot.api.repository.sql.jpa.ProjectJpaRepository;
import by.grsu.iot.api.repository.sql.project.thing.DeviceRepository;
import by.grsu.iot.api.repository.sql.project.thing.SensorRepository;
import by.grsu.iot.api.repository.sql.user.UserRepository;
import by.grsu.iot.api.util.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectRepositoryImpl.class);

    private final ProjectJpaRepository projectJpaRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final EntityFactory entityFactory;

    public ProjectRepositoryImpl(
            ProjectJpaRepository projectJpaRepository,
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            SensorRepository sensorRepository,
            EntityFactory entityFactory) {
        this.projectJpaRepository = projectJpaRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.entityFactory = entityFactory;
    }

    @Override
    public Project create(final String name, final String username, final String title) {
        User user = userRepository.getByUsername(username);
        Set<Project> projects = getUserProjectsByUser(user);

        Project newProject = entityFactory.createProject();
        newProject.setName(name);
        newProject.setTitle(title);

        newProject = projectJpaRepository.save(newProject);

        projects.add(newProject);
        user.setProjects(projects);
        userRepository.update(user);

        newProject.setUser(user);
        return update(newProject);
    }

    @Override
    public Project update(final Project project) {
        Project p = SerializationUtils.clone(project);

        p.setUpdated(TimeUtil.getCurrentDate());
        return projectJpaRepository.save(p);
    }

    @Override
    public Project getById(final Long id) {
        return projectJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Project> getUserProjectsByUser(final User user) {
        User u = SerializationUtils.clone(user);

        return projectJpaRepository.findProjectsByUser(u).orElse(new HashSet<>());
    }

    @Override
    public boolean isExist(Long id) {
        return projectJpaRepository.existsById(id);
    }

    @Override
    public boolean delete(Long id) {
        Project project = getById(id);

        if (project == null) {
            return false;
        }

        deviceRepository.getProjectDeviceIds(id).forEach(deviceId -> deviceRepository.delete(deviceId));
        sensorRepository.getProjectSensorIds(id).forEach(sensorId -> sensorRepository.delete(sensorId));

        projectJpaRepository.deleteById(project.getId());

        return !isExist(id);
    }

    @Override
    public String getProjectOwnerUsername(Long project) {
        Long userId = projectJpaRepository.findUserId(project);

        return userRepository.findUsername(userId);
    }

    @Override
    public List<Project> getByIds(List<Long> projectsId) {
        return projectJpaRepository.findAllById(projectsId);
    }

    @Override
    public List<Long> getUserProjectsIds(String username) {
        return projectJpaRepository.findProjectsIdByUserId(userRepository.getUserId(username));
    }

    @Override
    public Integer getUserProjectsSize(String username) {
        return getUserProjectsIds(username).size();
    }

    @Override
    public Integer getProjectIotThingSize(Long projectId) {
        return deviceRepository.getDevicesSize(projectId)
                + sensorRepository.getSensorsSize(projectId);
    }

    @Override
    public String getProjectOwnerUsernameByResourceId(Long resourceId) {
        Long userId = projectJpaRepository.findUserIdByResourceId(resourceId);

        return userRepository.findUsername(userId);
    }

    @Override
    public Page<Project> getPage(User user, Pageable pageable) {
        return projectJpaRepository.findAllByUser(user, pageable);
    }

    @Override
    public boolean hasUserOwnerProject(String username, Long projectId) {
        return username.equals(getProjectOwnerUsername(projectId));
    }
}
