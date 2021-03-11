package by.grsu.iot.service.domain.form;

import javax.validation.constraints.NotNull;

public class ProjectFormUpdate implements Form {

    @NotNull
    private String name;

    @NotNull
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
