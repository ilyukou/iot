package by.grsu.iot.service.domain.request.project;

import by.grsu.iot.service.annotation.StringValidation;
import by.grsu.iot.service.domain.DataTransferObject;

public class ProjectForm implements DataTransferObject {

    @StringValidation(min = 4, max = 16, spaceAllowed = true)
    private String name;

    @StringValidation(min = 0, max = 128, spaceAllowed = true, required = false)
    private String title;

    public ProjectForm(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public ProjectForm() {
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
