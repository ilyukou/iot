package by.grsu.iot.api.service.impl;


import by.grsu.iot.api.dto.ProjectThing;
import by.grsu.iot.api.exception.BadRequestException;
import by.grsu.iot.api.exception.EntityNotFoundException;
import by.grsu.iot.api.exception.NotAccessForOperationException;
import by.grsu.iot.api.service.activemq.EntityProducer;
import by.grsu.iot.api.service.interf.ProjectService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.*;
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
import java.util.Set;
import java.util.stream.Collectors;

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
        if (!userRepository.isExistByUsername(username)){
            throw new EntityNotFoundException("Not found user with such username={" + username + "}");
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
    public Project update(Long id, String name, String title, String username) {
        if (!projectRepository.isExist(id)) {
            throw new EntityNotFoundException("Project does not exist with given id {" + id + "}");
        }

        Project project = getById(id, username);

        if (!project.getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with given username={" + username + "}");
        }

        if(name != null){
            project.setName(name);
        }

        if(title != null){
            project.setTitle(title);
        }

        project = update(project);

        entityProducer.sendMessage(project, ActActiveMQ.UPDATE);

        return project;
    }

    @Override
    public Project getById(Long id, String username) {
        if (!projectRepository.isExist(id)) {
            throw new EntityNotFoundException("Project does not exist with given id {" + id + "}");
        }

        Project project = projectRepository.getById(id);

        if (!project.getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with giver username {" + username + "}");
        }

        return project;
    }

    @Override
    public boolean deleteById(Long id, String username) {

        if (!projectRepository.isExist(id)) {
            throw new EntityNotFoundException("Project does not exist with given id {" + id + "}");
        }

        Project project = projectRepository.getById(id);

        if (!project.getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with giver username {" + username + "}");
        }


        boolean result = projectRepository.delete(id);

        entityProducer.sendMessage(project, ActActiveMQ.DELETE);

        return result;
    }

    @Override
    public Project getById(Long projectId) {
        return projectRepository.getById(projectId);
    }

    @Override
    public List<Project> getProjectsFromPage(Integer count, String username) {
        User user = userRepository.getByUsername(username);

        if(user == null){
            throw new EntityNotFoundException("User does not exist with given username={" + username + "}");
        }

        if(user.getProjects().size() == 0){
            return new ArrayList<>();
        };

        return getProjectFromTo((count - 1) * PROJECT_PER_PAGE, count * PROJECT_PER_PAGE, new LinkedList<>(user.getProjects()), count);
    }

    @Override
    public Integer getCountOfProjectPage(String requestedUsername, String usernameRequestingThis) {
        Set<Project> projectSet;

        if(requestedUsername.equals(usernameRequestingThis)){
            projectSet = userRepository.getByUsername(requestedUsername).getProjects();
        } else {
            projectSet = userRepository.getByUsername(requestedUsername).getProjects().stream()
                    .filter(project -> project.getAccessType().equals(AccessType.PUBLIC))
                    .collect(Collectors.toSet());
        }

        if(projectSet.size() == 0){
            return 0;
        }

        if(projectSet.size() <= PROJECT_PER_PAGE){
            return 1;
        }

        if(projectSet.size() % PROJECT_PER_PAGE == 0){
            return Math.toIntExact(projectSet.size() / PROJECT_PER_PAGE);
        }

        int c = Math.toIntExact(projectSet.size() % PROJECT_PER_PAGE);

        return Math.toIntExact((projectSet.size() - c) / PROJECT_PER_PAGE) + 1;
    }

    @Override
    public List<ProjectThing> getThings(Long id, String username) {
        List<ProjectThing> projectThings = new ArrayList<>();

        Project project = projectRepository.getById(id);

        if(project == null){
            throw new EntityNotFoundException("Not found Project with such id={" + id + "}");
        }

        project.getDevices().forEach(device -> projectThings.add(new ProjectThing(device)));

        return projectThings;
    }

    private List<Project> getProjectFromTo(Long from, Long to, List<Project> projects, Integer count){

        if(from > projects.size()){
            throw new BadRequestException("count", "Project size={" + projects.size() + "}, " +
                    "last page is {" + Math.ceil(projects.size() / PROJECT_PER_PAGE) + ", " +
                    "you required page={" + count + "}, " +
                    "project size per page={"+PROJECT_PER_PAGE+"}");
        }

        if(to > projects.size()){
            to = (long) projects.size();
        }

        return projects.subList(from.intValue(), to.intValue());
    }
}
