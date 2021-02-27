package by.grsu.iot.api.dto;

import java.util.Date;

public class IotThingDto {

    private Long id;
    private String name;
    private String token;
    private Long activity;
    private Long update;
    private Long create;

    public IotThingDto(Long id, String name, String token, Date activity, Date update, Date create) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.activity = activity.getTime();
        this.update = update.getTime();
        this.create = create.getTime();
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
