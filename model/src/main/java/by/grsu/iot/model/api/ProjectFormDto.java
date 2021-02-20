package by.grsu.iot.model.api;

public class ProjectFormDto {
    private String name;

    private String title;

    public ProjectFormDto(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public ProjectFormDto() {
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
