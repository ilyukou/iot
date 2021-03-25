package by.grsu.iot.api.dto.thing;

import by.grsu.iot.model.sql.Device;

public class IotThingDto {

    private Long id;
    private String name;
    private String token;
    private Long activity;
    private Long update;
    private Long create;

    public IotThingDto(Device device) {
        this.id = device.getId();
        this.name = device.getName();
        this.token = device.getToken();

        if (device.getActive() != null){
            this.activity = device.getActive().getTime();
        }

        if (device.getUpdated() != null){
            this.update = device.getUpdated().getTime();
        }

        if (device.getCreated() != null){
            this.create = device.getCreated().getTime();
        }
    }

    public IotThingDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getActivity() {
        return activity;
    }

    public void setActivity(Long activity) {
        this.activity = activity;
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    public Long getCreate() {
        return create;
    }

    public void setCreate(Long create) {
        this.create = create;
    }
}
