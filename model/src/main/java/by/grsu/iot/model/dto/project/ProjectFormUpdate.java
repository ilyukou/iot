package by.grsu.iot.model.dto.project;

import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

/**
 * Used to send data to update a project
 *
 * @author Ilyukou Ilya
 */
public class ProjectFormUpdate implements DataTransferObject {

    @StringValidation(min = 4, max = 16, spaceAllowed = true)
    private String name;

    @StringValidation(min = 0, max = 128, spaceAllowed = true)
    private String title;

    public ProjectFormUpdate(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public ProjectFormUpdate() {
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
