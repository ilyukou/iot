package by.grsu.iot.api.dto;


import by.grsu.iot.model.elastic.ProjectElasticsearch;
import by.grsu.iot.model.sql.Device;
import by.grsu.iot.model.sql.Project;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Project}
 */
public class ProjectDto {

    private String name;

    private String title;

    private Long id;

    public ProjectDto(Project project) {
        this.name = project.getName();
        this.title = project.getTitle();
        this.id = project.getId();
    }

    public ProjectDto(ProjectElasticsearch projectElasticsearch) {
        this.name = projectElasticsearch.getName();
        this.title = projectElasticsearch.getTitle();
        this.id = projectElasticsearch.getProjectId();
    }

    public ProjectDto() {
    }

    public ProjectDto(String name, String title, Long projectId) { ;
        this.name = name;
        this.title = title;
        this.id = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
