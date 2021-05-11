package by.grsu.iot.model.dto.resource;

import by.grsu.iot.model.dto.DataTransferObject;
import by.grsu.iot.model.sql.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link Resource}
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto implements DataTransferObject {

    private Long project;
    private String filename;

    public ResourceDto(Resource resource) {
        this.project = resource.getProject().getId();
        this.filename = resource.getFileName();
    }
}
