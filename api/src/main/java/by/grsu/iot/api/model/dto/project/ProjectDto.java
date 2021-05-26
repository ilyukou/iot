package by.grsu.iot.api.model.dto.project;


import by.grsu.iot.api.model.dto.DataTransferObject;
import by.grsu.iot.api.model.sql.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link Project}
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
