package by.grsu.iot.model.mongo;

import by.grsu.iot.model.domain.DeviceState;
import by.grsu.iot.model.domain.DeviceStateResult;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
public class DeviceStateMongoDocument {

    @Id
    private String id;

    private String token;

    private String state;

    private long time;

    private DeviceStateResult result;

    public DeviceStateMongoDocument(String id, String token, String state, long time, DeviceStateResult result) {
        this.id = id;
        this.token = token;
        this.state = state;
        this.time = time;
        this.result = result;
    }

    public DeviceStateMongoDocument(DeviceState deviceState) {
        this.id = deviceState.getId();
        this.token = deviceState.getToken();
        this.state = deviceState.getState();
        this.time = deviceState.getTime();
        this.result = deviceState.getResult();
    }

    public DeviceStateMongoDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public DeviceStateResult getResult() {
        return result;
    }

    public void setResult(DeviceStateResult result) {
        this.result = result;
    }
}
