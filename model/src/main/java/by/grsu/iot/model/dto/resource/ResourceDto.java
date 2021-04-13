package by.grsu.iot.model.dto.resource;

import by.grsu.iot.model.dto.DataTransferObject;
import by.grsu.iot.model.sql.Resource;

/**
 * DTO for {@link Resource}
 *
 * @author Ilyukou Ilya
 */
public class ResourceDto implements DataTransferObject {

    private Long project;
    private String filename;

    public ResourceDto(Resource resource) {
        this.project = resource.getProject().getId();
        this.filename = resource.getFileName();
    }

    public ResourceDto() {
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
