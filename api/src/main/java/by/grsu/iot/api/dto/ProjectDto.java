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

    private Long id;

    private List<Long> devices;

    private String name;

    private String title;

    private Long owner;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.title = project.getTitle();

        if (project.getUser() != null) {
            this.owner = project.getUser().getId();
        }

        if (project.getDevices() != null) {
            this.devices = project.getDevices().stream()
                    .filter(Device::isActive)
                    .map(Device::getId)
                    .collect(Collectors.toList());
        }
    }

    public ProjectDto(ProjectElasticsearch projectElasticsearch) {
        this.id = projectElasticsearch.getProjectId();
        this.name = projectElasticsearch.getName();
        this.title = projectElasticsearch.getTitle();
        this.owner = projectElasticsearch.getOwner();
    }

    public ProjectDto() {
    }

    public ProjectDto(Long id, List<Long> devices, String name, String title, Long owner) {
        this.id = id;
        this.devices = devices;
        this.name = name;
        this.title = title;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getDevices() {
        return devices;
    }

    public void setDevices(List<Long> devices) {
        this.devices = devices;
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

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
