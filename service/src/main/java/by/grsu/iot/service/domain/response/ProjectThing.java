package by.grsu.iot.service.domain.response;

import by.grsu.iot.model.sql.Device;

import java.io.Serializable;

public class ProjectThing implements Serializable {

    private final String DEVICE_ENTITY_NAME = "device";

    private String entity;
    private Long id;

    public ProjectThing(String entity, Long id) {
        this.entity = entity;
        this.id = id;
    }

    public ProjectThing(Device device) {
        this.entity = DEVICE_ENTITY_NAME;
        this.id = device.getId();
    }

    public ProjectThing() {
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
