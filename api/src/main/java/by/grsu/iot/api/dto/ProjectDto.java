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

    private List<Long> devices;

    private String name;

    private String title;

    private Long id;

    public ProjectDto(Project project) {
        this.name = project.getName();
        this.title = project.getTitle();
        this.id = project.getId();

        if (project.getDevices() != null) {
            this.devices = project.getDevices().stream()
                    .filter(Device::isActive)
                    .map(Device::getId)
                    .collect(Collectors.toList());
        }
    }

    public ProjectDto(ProjectElasticsearch projectElasticsearch) {
        this.name = projectElasticsearch.getName();
        this.title = projectElasticsearch.getTitle();
        this.id = projectElasticsearch.getProjectId();
    }

    public ProjectDto() {
    }

    public ProjectDto(List<Long> devices, String name, String title, Long projectId) {
        this.devices = devices;
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
}
