package by.grsu.iot.service.interf;

import by.grsu.iot.model.api.ProjectForm;
import by.grsu.iot.service.domain.ProjectThing;
import by.grsu.iot.model.sql.Project;

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

    List<ProjectThing> getThings(Long id, String username);
}
