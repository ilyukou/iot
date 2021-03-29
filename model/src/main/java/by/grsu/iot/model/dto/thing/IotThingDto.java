package by.grsu.iot.model.dto.thing;

import by.grsu.iot.model.sql.IotThing;

public class IotThingDto {

    private Long id;
    private String name;
    private String token;
    private Long activity;
    private Long update;
    private Long create;

    public IotThingDto(IotThing iotThing) {
        this.id = iotThing.getId();
        this.name = iotThing.getName();
        this.token = iotThing.getToken();

        if (iotThing.getActive() != null){
            this.activity = iotThing.getActive().getTime();
        }

        if (iotThing.getUpdated() != null){
            this.update = iotThing.getUpdated().getTime();
        }

        if (iotThing.getCreated() != null){
            this.create = iotThing.getCreated().getTime();
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
