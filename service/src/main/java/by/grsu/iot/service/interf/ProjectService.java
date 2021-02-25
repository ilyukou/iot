package by.grsu.iot.service.interf;

import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.model.sql.IotThing;
import by.grsu.iot.service.domain.ProjectThing;
import by.grsu.iot.model.sql.Project;

import java.util.Collection;
import java.util.List;

public interface ProjectService {
    Project create(ProjectForm projectForm, String username);

    Project getById(Long id, String username);

    Project update(Project project);

    Project update(Long id, ProjectForm projectForm, String username);

    boolean deleteById(Long id, String username);

    Project getById(Long projectId);

    List<Project> getProjectsFromPage(Integer count, String username);

    Integer getCountOfProjectPage(String requestedUsername, String usernameRequestingThis);

    List<? extends IotThing> getThings(Long id, String username);

    Integer getCountThingPages(Long projectId, String username);

    List<? extends IotThing> getThingPage(Long projectId, Integer count, String username);
}
