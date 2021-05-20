package by.grsu.iot.repository.impl;

import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.factory.EntityFactory;
import by.grsu.iot.repository.interf.DeviceRepository;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.SensorRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.repository.jpa.ProjectJpaRepository;
import by.grsu.iot.util.service.TimeUtil;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ProjectRepositoryImpl(
            ProjectJpaRepository projectJpaRepository,
            UserRepository userRepository,
            DeviceRepository deviceRepository,
            SensorRepository sensorRepository
    ) {
        this.projectJpaRepository = projectJpaRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Project create(final String name, final String username, final String title) {
        User user = userRepository.getByUsername(username);
        Set<Project> projects = getUserProjectsByUser(user);

        Project newProject = EntityFactory.createProject();
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
    public boolean hasUserOwnerProject(String username, Long projectId) {
        return username.equals(getProjectOwnerUsername(projectId));
    }
}
