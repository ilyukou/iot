package by.grsu.iot.service.impl;

import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.service.domain.ProjectThing;
import by.grsu.iot.service.activemq.EntityProducer;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.exception.EntityNotFoundException;
import by.grsu.iot.service.exception.ExceptionUtil;
import by.grsu.iot.service.exception.NotAccessForOperationException;
import by.grsu.iot.service.interf.ProjectService;
import by.grsu.iot.model.activemq.ActActiveMQ;
import by.grsu.iot.model.sql.*;
import by.grsu.iot.repository.interf.ProjectRepository;
import by.grsu.iot.repository.interf.UserRepository;
import by.grsu.iot.service.validation.factory.DataBinderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

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
    private static final Long DEVICE_PER_PAGE = 25L;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final EntityProducer entityProducer;
    private final DataBinderFactory dataBinderFactory;

    @Autowired
    public ProjectServiceImpl(
            UserRepository userRepository,
            ProjectRepository projectRepository,
            EntityProducer entityProducer,
            DataBinderFactory dataBinderFactory
    ) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.entityProducer = entityProducer;
        this.dataBinderFactory = dataBinderFactory;
    }

    @Override
    public Project create(ProjectForm projectForm, String username) {
        DataBinder dataBinder = dataBinderFactory.createDataBinder(projectForm);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ExceptionUtil.throwException(dataBinder.getBindingResult());
        }

        if (!userRepository.isExistByUsername(username)){
            throw new EntityNotFoundException("Not found user with such username={" + username + "}");
        }

        Project project = projectRepository.create(projectForm.getName(), username, projectForm.getTitle());

        entityProducer.sendMessage(project, ActActiveMQ.CREATE);

        return project;
    }

    @Override
    public Project update(Project project) {
        return projectRepository.update(project);
    }

    @Override
    public Project update(Long id, ProjectForm projectForm, String username) {
        DataBinder dataBinder = dataBinderFactory.createDataBinder(projectForm);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            ExceptionUtil.throwException(dataBinder.getBindingResult());
        }

        if (!projectRepository.isExist(id)) {
            throw new EntityNotFoundException("Project does not exist with given id {" + id + "}");
        }

        Project project = getById(id, username);

        if (!project.getUser().getUsername().equals(username)) {
            throw new NotAccessForOperationException("Project does not belong this user with given username={" + username + "}");
        }

        if(projectForm.getName() != null){
            project.setName(projectForm.getName());
        }

        if(projectForm.getTitle() != null){
            project.setTitle(projectForm.getTitle());
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

        List<Project> projects = user.getProjects().stream().sorted().collect(Collectors.toList());

        return getProjectFromTo((count - 1) * PROJECT_PER_PAGE, count * PROJECT_PER_PAGE, projects);
    }

    @Override
    public Integer getCountOfProjectPage(String requestedUsername, String usernameRequestingThis) {
        Set<Project> projectSet;

        if(requestedUsername == null){
            requestedUsername = usernameRequestingThis;
        }

        if(requestedUsername.equals(usernameRequestingThis)){
            projectSet = userRepository.getByUsername(requestedUsername).getProjects();
        } else {
            projectSet = userRepository.getByUsername(requestedUsername).getProjects().stream()
                    .filter(project -> project.getAccessType().equals(AccessType.PUBLIC))
                    .collect(Collectors.toSet());
        }

        return getCountOfPage(projectSet.size(), PROJECT_PER_PAGE );
    }

    @Override
    public List<? extends IotThing> getThings(Long id, String username) {

        Project project = projectRepository.getById(id);

        if(project == null){
            throw new EntityNotFoundException("Not found Project with such id={" + id + "}");
        }

        return new ArrayList<>(project.getDevices());
    }

    @Override
    public Integer getCountThingPages(Long projectId, String username) {
        Project project = projectRepository.getById(projectId);

        if (project == null){
            throw new EntityNotFoundException("Not found project with such id={" + projectId + "}");
        }

        if (!project.getUser().getUsername().equals(username)){
            throw new NotAccessForOperationException("That user not has project with such id");
        }

        return getCountOfPage(project.getDevices().size(), DEVICE_PER_PAGE );
    }

    @Override
    public List<? extends IotThing> getThingPage(Long projectId, Integer count, String username) {
        Project project = projectRepository.getById(projectId);

        if(project == null){
            throw new EntityNotFoundException("Project does not exist with given id={" + projectId + "}");
        }

        List<? extends IotThing> projects = project.getDevices().stream().sorted().collect(Collectors.toList());

        return getIotThingFromTo((count - 1) * PROJECT_PER_PAGE, count * PROJECT_PER_PAGE, projects);
    }

    private List<Project> getProjectFromTo(Long from, Long to, List<Project> projects){

        if(from > projects.size()){
            throw new BadRequestException("count", "Not exist such page");
        }

        if(to > projects.size()){
            to = (long) projects.size();
        }

        return projects.subList(from.intValue(), to.intValue());
    }

    private List<? extends IotThing> getIotThingFromTo(Long from, Long to, List<? extends IotThing> devices){

        if(from > devices.size()){
            throw new BadRequestException("count", "Not exist such page");
        }

        if(to > devices.size()){
            to = (long) devices.size();
        }

        return devices.subList(from.intValue(), to.intValue());
    }

    private Integer getCountOfPage(Integer arraySize, Long elementPerPage){
        if(arraySize == 0){
            return 0;
        }

        if(arraySize <= elementPerPage){
            return 1;
        }

        if(arraySize % elementPerPage == 0){
            return Math.toIntExact(arraySize / elementPerPage);
        }

        int c = Math.toIntExact(arraySize % elementPerPage);

        return Math.toIntExact((arraySize - c) / elementPerPage) + 1;
    }
}
