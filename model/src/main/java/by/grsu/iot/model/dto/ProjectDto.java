package by.grsu.iot.model.dto;


import by.grsu.iot.model.sql.Project;

/**
 * DTO for {@link Project}
 */
public class ProjectDto {

    private String name;

    private String title;

    private Long id;

    private Long things;

    public ProjectDto(Project project) {
        this.name = project.getName();
        this.title = project.getTitle();
        this.id = project.getId();
        this.things = (long) project.getDevices().size();
    }

    public ProjectDto() {
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

    public Long getThings() {
        return things;
    }

    public void setThings(Long things) {
        this.things = things;
    }
}