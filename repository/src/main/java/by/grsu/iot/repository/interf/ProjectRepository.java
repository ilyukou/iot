package by.grsu.iot.repository.interf;

import by.grsu.iot.model.sql.Project;

import java.util.List;

public interface ProjectRepository {

    Project create(String name, String username, String title);

    Project update(Project project);

    Project getById(Long id);

    boolean disableProjectByProjectId(Long projectId);

    boolean isExist(Long id);

    List<Long> getProjects(Long userId);

    List<Project> getProjectsByIds(List<Long> ids);

    boolean isUserProject(Long projectId, String username);
}
