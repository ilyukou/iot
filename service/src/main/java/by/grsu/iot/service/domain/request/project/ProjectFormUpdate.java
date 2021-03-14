package by.grsu.iot.service.domain.request.project;

import by.grsu.iot.service.annotation.Validation;
import by.grsu.iot.service.domain.DataTransferObject;

public class ProjectFormUpdate implements DataTransferObject {

    @Validation(min = 4, max = 16, spaceAllowed = true, required = false)
    private String name;

    @Validation(min = 0, max = 128, spaceAllowed = true, required = false)
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
