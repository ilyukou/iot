package by.grsu.iot.api.service.interf;


import by.grsu.iot.model.sql.Project;

import java.util.List;

public interface ProjectService {
    Project create(String name, String username, String title);

    Project getById(Long id, String username);

    Project update(Project project);

    void update(Long id, String name, String username);

    boolean deleteById(Long id, String username);

    List<Long> getProjectIdsByUsername(String username);

    Project getById(Long projectId);

    List<Project> getByIds(List<Long> ids, String username);

    boolean canUserReadProjects(List<Long> ids, String username);

    boolean canUserReadProject(Long id, String username);

    List<Project> getProjectsFromPage(Integer count, String username);
}
