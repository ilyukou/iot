package by.grsu.iot.model.dto.project;


import by.grsu.iot.model.dto.DataTransferObject;
import by.grsu.iot.model.sql.Project;

/**
 * DTO for {@link Project}
 *
 * @author Ilyukou Ilya
 */
public class ProjectDto implements DataTransferObject {

    private String name;

    private String title;

    private Long id;

    private String resource;

    public ProjectDto(Project project) {
        this.name = project.getName();
        this.title = project.getTitle();
        this.id = project.getId();

        if (project.getResource() != null){
            this.resource = project.getResource().getFileName();
        }
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
