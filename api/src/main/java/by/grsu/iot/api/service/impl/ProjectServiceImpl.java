package by.grsu.iot.api.service.impl;


import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.exception.EntityNotFoundException;
import by.grsu.iot.api.service.activemq.EntityProducer;
import by.grsu.iot.api.service.interf.ProjectService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.Project;
import by.grsu.iot.model.sql.RoleType;
import by.grsu.iot.model.sql.User;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private static final Long PROJECT_PER_PAGE = 10L;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final EntityProducer entityProducer;

    @Autowired
    public ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository,
                              EntityProducer entityProducer) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.entityProducer = entityProducer;
    }

    @Override
    public Project create(String name, String username, String title) {

        if (name == null || username == null || title == null) {
            String ms = "Name or username must be not null";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        Project project = projectRepository.create(name, username, title);

        entityProducer.sendMessage(project, ActActiveMQ.CREATE);

        return project;
    }

    @Override
    public Project update(Project project) {
        return projectRepository.update(project);
    }

    @Override
    public void update(Long id, String name, String username) {
        Project project = getById(id, username);

        if (project == null) {
            String ms = "Project does not exist with given id {" + id + "}";
            LOG.info(ms);
            throw new EntityNotFoundException(ms);
        }

        if (!project.getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        project.setName(name);

        project = update(project);

        entityProducer.sendMessage(project, ActActiveMQ.UPDATE);
    }

    @Override
    public Project getById(Long id, String username) {
        Project project = projectRepository.getById(id);

        if (project == null) {
            String ms = "Project does not exist with given id {" + id + "}";
            LOG.info(ms);
            throw new EntityNotFoundException(ms);
        }

        if (!project.getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }

        return project;
    }

    @Override
    public boolean deleteById(Long id, String username) {
        Project project = projectRepository.getById(id);

        if (project == null) {
            return false;
        }

        if (!project.getUser().getUsername().equals(username)) {
            String ms = "Project does not belong this user with giver username {" + username + "}";
            LOG.info(ms);
            throw new IllegalArgumentException(ms);
        }


        boolean result = projectRepository.disableProjectByProjectId(id);

        entityProducer.sendMessage(project, ActActiveMQ.DELETE);

        return result;
    }

    @Override
    public List<Long> getProjectIdsByUsername(String username) {
        User user = userRepository.getByUsername(username);

        if (user == null) {
            String ms = "Not found User by username {" + username + "}";
            LOG.info(ms);
            throw new EntityNotFoundException(ms);
        }

        return projectRepository.getProjects(user.getId());
    }

    @Override
    public Project getById(Long projectId) {
        return projectRepository.getById(projectId);
    }

    @Override
    public List<Project> getByIds(List<Long> ids, String username) {
        if(ids.size() == 0 || ids.size() > 10){
            throw new IllegalArgumentException();
        }

        if(!canUserReadProjects(ids, username)){
            throw new IllegalArgumentException();
        }

        return projectRepository.getProjectsByIds(ids);
    }

    @Override
    public boolean canUserReadProjects(List<Long> ids, String username) {
        for (Long id: ids){
            if(!canUserReadProject(id, username)){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canUserReadProject(Long id, String username) {
        if(userRepository.getUserRole(username) == RoleType.Admin){
            return true;
        }

        if(projectRepository.isUserProject(id, username)){
            return true;
        }

        return false;
    }

    @Override
    public List<Project> getProjectsFromPage(Integer count, String username) {
        User user = userRepository.getByUsername(username);

        if(user == null){
            throw new BadRequestException();
        }

        if(user.getProjects().size() == 0){
            return new ArrayList<>();
        };

        return getProjectFromTo(count * PROJECT_PER_PAGE, (count + 1) * PROJECT_PER_PAGE, new LinkedList<>(user.getProjects()));
    }

    private List<Project> getProjectFromTo(Long from, Long to, List<Project> projects){

        if(from > projects.size() || to > projects.size() || from >= to){
            throw new BadRequestException();
        }

        return projects.subList(from.intValue(), to.intValue());
    }
}
