package by.grsu.iot.api.dto;

import java.util.Date;

public class IotThingDto {

    private Long id;
    private String name;
    private String token;
    private Date activity;
    private Date update;
    private Date create;

    public IotThingDto(Long id, String name, String token, Date activity, Date update, Date create) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.activity = activity;
        this.update = update;
        this.create = create;
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

    public Date getActivity() {
        return activity;
    }

    public void setActivity(Date activity) {
        this.activity = activity;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }
}
