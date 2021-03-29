package by.grsu.iot.model.dto.thing.sensor;

import by.grsu.iot.model.annotation.StringValidation;
import by.grsu.iot.model.dto.DataTransferObject;

/**
 * Used to send data to create a sensor
 *
 * @author Ilyukou Ilya
 */
public class SensorForm implements DataTransferObject {

    private Long project;

    @StringValidation(min = 2, max = 16, spaceAllowed = true)
    private String name;

    public SensorForm(Long project, String name) {
        this.project = project;
        this.name = name;
    }

    public SensorForm() {
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
