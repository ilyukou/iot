package by.grsu.iot.api.service.interf;


import by.grsu.iot.api.dto.ProjectThing;
import by.grsu.iot.model.sql.Project;

import java.util.List;

public interface ProjectService {
    Project create(String name, String username, String title);

    Project getById(Long id, String username);

    Project update(Project project);

    Project update(Long id, String name, String title, String username);

    boolean deleteById(Long id, String username);

    Project getById(Long projectId);

    List<Project> getProjectsFromPage(Integer count, String username);

    Integer getCountOfProjectPage(String requestedUsername, String usernameRequestingThis);

    List<ProjectThing> getThings(Long id, String username);
}
