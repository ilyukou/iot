package by.grsu.iot.api.model.dto.project;

import by.grsu.iot.api.model.annotation.RequiredField;
import by.grsu.iot.api.model.annotation.StringValidation;
import by.grsu.iot.api.model.dto.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Used to send data to create a project
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectForm implements DataTransferObject {

    @RequiredField
    @StringValidation(min = 4, max = 16, spaceAllowed = true)
    private String name;

    @StringValidation(min = 0, max = 128, spaceAllowed = true)
    private String title;
}
