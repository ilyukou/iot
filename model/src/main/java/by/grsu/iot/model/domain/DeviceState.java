package by.grsu.iot.model.domain;

import by.grsu.iot.model.mongo.DeviceStateMongoDocument;
import by.grsu.iot.model.sql.Device;

public class DeviceState {

    private String id;

    private String token;

    private String state;

    private long time;

    private DeviceStateResult result;

    public DeviceState(String id, String token, String state, long time, DeviceStateResult result) {
        this.id = id;
        this.token = token;
        this.state = state;
        this.time = time;
        this.result = result;
    }

    public DeviceState(String token, String state, long time, DeviceStateResult result) {
        this.token = token;
        this.state = state;
        this.time = time;
        this.result = result;
    }

    public DeviceState(String token, String state) {
        this.token = token;
        this.state = state;
    }

    public DeviceState(Device device) {
        this.state = device.getState();
    }

    public DeviceState(String state) {
        this.state = state;
    }

    public DeviceState(DeviceStateMongoDocument deviceStateMongoDocument) {
        this.id = deviceStateMongoDocument.getId();
        this.token = deviceStateMongoDocument.getToken();
        this.state = deviceStateMongoDocument.getState();
        this.time = deviceStateMongoDocument.getTime();
        this.result = deviceStateMongoDocument.getResult();
    }

    public DeviceState() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
